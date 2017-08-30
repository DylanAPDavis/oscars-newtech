package net.es.oscars.resv.svc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.es.oscars.pce.exc.DuplicateConnectionIdException;
import net.es.oscars.pce.exc.PCEException;
import net.es.oscars.pce.TopPCE;
import net.es.oscars.pss.PSSException;
import net.es.oscars.pss.svc.PssResourceService;
import net.es.oscars.resv.dao.ConnectionRepository;
import net.es.oscars.resv.ent.*;
import net.es.oscars.st.prov.ProvState;
import net.es.oscars.st.resv.ResvState;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
public class ResvService {

    @Autowired
    public ResvService(TopPCE topPCE, ConnectionRepository connRepo, PssResourceService pssResourceService) {
        this.topPCE = topPCE;
        this.connRepo = connRepo;
        this.pssResourceService = pssResourceService;
    }

    private TopPCE topPCE;

    private ConnectionRepository connRepo;

    private PssResourceService pssResourceService;


    // basically DB stuff

    public void save(ConnectionE resv) {
        connRepo.save(resv);
    }

    public void delete(ConnectionE resv) {
        connRepo.delete(resv);
    }

    public List<ConnectionE> findAll() {
        return connRepo.findAll();
    }

    public Optional<ConnectionE> findByConnectionId(String connectionId) {
        return connRepo.findByConnectionId(connectionId);
    }

    public Stream<ConnectionE> ofResvState(ResvState resvState) {
        return connRepo.findAll().stream().filter(c -> c.getStates().getResv().equals(resvState));

    }
    public Stream<ConnectionE> ofHeldTimeout(Integer timeoutMs) {
        return connRepo.findAll().stream()
                .filter(c -> c.getStates().getResv().equals(ResvState.HELD))
                .filter(c -> (c.getSchedule().getSubmitted().getTime() + timeoutMs < new Date().getTime()));

    }
    public Stream<ConnectionE> ofProvState(ProvState provState) {
        return connRepo.findAll().stream().filter(c -> c.getStates().getProv().equals(provState));

    }


    // business logic


    public void provFailed(ConnectionE c) {
        c.getStates().setProv(ProvState.FAILED);
        connRepo.save(c);
    }

    public void generated(ConnectionE c) {
        c.getStates().setProv(ProvState.GENERATED);
        connRepo.save(c);
    }

    public void abort(ConnectionE c) {
        this.deleteReserved(c);

        pssResourceService.release(c);
        c.getStates().setResv(ResvState.IDLE_WAIT);
        connRepo.save(c);
    }

    public void timeout(ConnectionE c) {
        this.deleteReserved(c);
        pssResourceService.release(c);

        c.getStates().setResv(ResvState.IDLE_WAIT);
        connRepo.save(c);
    }

    public void commit(ConnectionE c) {
        c.getStates().setResv(ResvState.IDLE_WAIT);
        try {
            pssResourceService.reserve(c);
            c.getStates().setProv(ProvState.READY);
            log.info("reserved PSS resources");
        } catch (PSSException ex) {
            log.error("PSS resource reservation error", ex);
            c.getStates().setProv(ProvState.FAILED);
        }

        connRepo.save(c);
    }

    public void hold(ConnectionE c) throws PSSException, PCEException {

        Optional<ConnectionE> maybeDuplicate = connRepo.findByConnectionId(c.getConnectionId());
        if (maybeDuplicate.isPresent()) {
            throw new DuplicateConnectionIdException("Duplicate connection id "+c.getConnectionId());
        }

        RequestedBlueprintE req = c.getSpecification().getRequested();

        List<Date> reservedSched = new ArrayList<>();
        Optional<ReservedBlueprintE> res = topPCE.makeReserved(req, c.getSpecification().getScheduleSpec(), reservedSched);

        // Reserved schedule list will contain [startDate, endDate]
        // Will be empty if the reservation failed
        c.setReservedSchedule(reservedSched);

        if (res.isPresent()) {
            c.setReserved(res.get());
            c.getStates().setResv(ResvState.HELD);
            c = connRepo.save(c);

            try {
                String pretty = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(c);
                //log.info(pretty);     // commented for output readability
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            log.error("Reservation Unsuccessful!");
            c.setReserved(ReservedBlueprintE.builder()
                    .vlanFlow(ReservedVlanFlowE.builder()
                            .junctions(new HashSet<>())
                            .mplsPipes(new HashSet<>())
                            .ethPipes(new HashSet<>())
                            .allPaths(new HashSet<>())
                            .containerConnectionId(c.getConnectionId())
                            .build())
                    .containerConnectionId(c.getConnectionId())
                    .build());
            c.getStates().setResv(ResvState.ABORTING);
            connRepo.save(c);
        }

    }

    // internal convenience

    private ConnectionE deleteReserved(ConnectionE c) {
        ReservedVlanFlowE currentFlow = c.getReserved().getVlanFlow();
        Set<ReservedEthPipeE> ethPipes = currentFlow.getEthPipes();
        Set<ReservedMplsPipeE> mplsPipes = currentFlow.getMplsPipes();

        Set<ReservedBandwidthE> reservedBandwidths = new HashSet<>();
        Set<ReservedVlanE> reservedVlans = new HashSet<>();
        for(ReservedEthPipeE pipe: ethPipes){
            // Get bandwidths
            reservedBandwidths.addAll(pipe.getReservedBandwidths());
            reservedBandwidths.addAll(pipe.getAJunction().getFixtures().stream().map(ReservedVlanFixtureE::getReservedBandwidth).collect(Collectors.toSet()));
            reservedBandwidths.addAll(pipe.getZJunction().getFixtures().stream().map(ReservedVlanFixtureE::getReservedBandwidth).collect(Collectors.toSet()));
            // Get pipe VLANs
            reservedVlans.addAll(pipe.getReservedVlans());
            // Get A junction VLANs
            reservedVlans.addAll(pipe.getAJunction().getReservedVlans());
            reservedVlans.addAll(pipe.getAJunction().getFixtures().stream()
                    .map(ReservedVlanFixtureE::getReservedVlans)
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet()));
            // Get Z junction VLANs
            reservedVlans.addAll(pipe.getZJunction().getReservedVlans());
            reservedVlans.addAll(pipe.getZJunction().getFixtures().stream()
                    .map(ReservedVlanFixtureE::getReservedVlans)
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet()));
        }
        for(ReservedMplsPipeE pipe: mplsPipes){
            // Get bandwidths
            reservedBandwidths.addAll(pipe.getReservedBandwidths());
            reservedBandwidths.addAll(pipe.getAJunction().getFixtures().stream().map(ReservedVlanFixtureE::getReservedBandwidth).collect(Collectors.toSet()));
            reservedBandwidths.addAll(pipe.getZJunction().getFixtures().stream().map(ReservedVlanFixtureE::getReservedBandwidth).collect(Collectors.toSet()));
            // Get A junction VLANs
            reservedVlans.addAll(pipe.getAJunction().getReservedVlans());
            reservedVlans.addAll(pipe.getAJunction().getFixtures().stream()
                    .map(ReservedVlanFixtureE::getReservedVlans)
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet()));
            // Get Z junction VLANs
            reservedVlans.addAll(pipe.getZJunction().getReservedVlans());
            reservedVlans.addAll(pipe.getZJunction().getFixtures().stream()
                    .map(ReservedVlanFixtureE::getReservedVlans)
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet()));
        }
        for(ReservedBandwidthE resvBw : reservedBandwidths){
            resvBw.setInBandwidth(0);
            resvBw.setEgBandwidth(0);
        }
        for(ReservedVlanE resvVlan : reservedVlans){
            resvVlan.setVlan(-1);
        }

        return c;
    }

    // Submits connection request to TopPCE but does NOT trigger persistence!
    public Boolean preCheck(ConnectionE c) throws PSSException, PCEException
    {
        RequestedBlueprintE req = c.getSpecification().getRequested();

        List<Date> chosenDates = new ArrayList<>();
        Optional<ReservedBlueprintE> res = topPCE.makeReserved(req, c.getSpecification().getScheduleSpec(), chosenDates);

        if (res.isPresent())
        {
            log.info("Pre-check on ConnectionID: " + c.getConnectionId() + " Successful");

            c.setReserved(res.get());
            return Boolean.TRUE;
        }

        log.info("Pre-check on ConnectionID: " + c.getConnectionId() + " Unsuccessful");
        return Boolean.FALSE;
    }

    /**
     * Populates the ArchivedBlueprintE of a Connection when it transitions into a final state from a reserved/active one.
     * This enables freeing of reserved resources but maintains tracking and archival info on the connection.
     * @param c ConnectionE to archive
     */
    public void archiveReservation(ConnectionE c)
    {
        ModelMapper modelMapper = new ModelMapper();
        ArchivedBlueprintE archival = modelMapper.map(c.getReserved(), ArchivedBlueprintE.class);

        log.debug("Reservation: " + c.getReserved().toString());
        log.debug("Archive: " + archival.toString());

        c.setArchivedResv(archival);
    }

}
