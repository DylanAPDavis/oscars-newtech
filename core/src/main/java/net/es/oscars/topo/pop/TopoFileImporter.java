package net.es.oscars.topo.pop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.topo.enums.Layer;
import net.es.oscars.topo.dao.UrnAdjcyRepository;
import net.es.oscars.topo.dao.UrnRepository;
import net.es.oscars.topo.ent.ReservableBandwidthE;
import net.es.oscars.topo.ent.ReservableVlanE;
import net.es.oscars.topo.ent.UrnAdjcyE;
import net.es.oscars.topo.ent.UrnE;
import net.es.oscars.dto.topo.enums.IfceType;
import net.es.oscars.dto.topo.enums.UrnType;
import net.es.oscars.topo.prop.TopoProperties;
import net.es.oscars.topo.serialization.UrnAdjcy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Slf4j
@Service
public class TopoFileImporter {
    private UrnRepository urnRepo;

    private UrnAdjcyRepository adjcyRepo;

    private TopoProperties topoProperties;

    @Autowired
    public TopoFileImporter(UrnRepository urnRepo, UrnAdjcyRepository adjcyRepo,
                            TopoProperties topoProperties) {
        this.urnRepo = urnRepo;
        this.adjcyRepo = adjcyRepo;
        this.topoProperties = topoProperties;
    }

    @Transactional
    public void startup() {
        log.info("Startup. Will attempt import from files set in topo.[devices|adcjies].filename properties.");
        if (topoProperties == null) {
            log.error("No 'topo' stanza in application properties! Skipping topology import.");
            return;
        }

        String devicesFilename = "./config/topo/" + topoProperties.getPrefix() + "-devices.json";

        String adjciesFilename = "./config/topo/" + topoProperties.getPrefix() + "-adjcies.json";

        try {
            this.importFromFile(false, devicesFilename, adjciesFilename);
        } catch (IOException ex) {
            log.error("Import failed! " + ex.getMessage());
        }
    }

    @Transactional
    public void importFromFile(boolean overwrite, String devicesFilename, String adjciesFilename) throws IOException {

        if (overwrite) {
            log.info("Overwrite set; deleting topology DB entries.");
            urnRepo.deleteAll();
            adjcyRepo.deleteAll();
        }

        List<Device> devices = importDevicesFromFile(devicesFilename, overwrite);
        log.info("Devices defined in file " + devicesFilename + " : " + devices.size());

        if (urnRepo.count() == 0) {
            log.info("URN DB empty. Will replace with input from devices file " + devicesFilename);
            List<UrnE> newUrns = this.urnsFromDevices(devices);

            urnRepo.save(newUrns);
            log.info("URNs defined from devices: " + newUrns.size());
        } else {
            log.info("Devices DB is not empty; skipping import");
        }


        List<UrnAdjcyE> newAdjcies = importAdjciesFromFile(adjciesFilename, overwrite);
        log.info("Defined adjacencies from adjacencies file: " + newAdjcies.size());

        List<UrnAdjcyE> deviceAdjcies = adjciesFromDevices(devices, overwrite);
        log.info("Implied adjacencies from devices file: " + deviceAdjcies.size());


        if (adjcyRepo.count() == 0) {
            log.info("Adjacencies DB empty. Will replace with those from files. ");
            newAdjcies.forEach(adjcyE -> {
                adjcyRepo.save(adjcyE);
            });

            deviceAdjcies.forEach(adjcyE -> {
                adjcyRepo.save(adjcyE);
            });

        } else {
            log.info("Adjacencies DB is not empty; skipping import");
        }

    }


    private List<UrnAdjcyE> adjciesFromDevices(List<Device> devices, boolean overwrite) {
        List<UrnAdjcyE> adjcies = new ArrayList<>();
        devices.forEach(d -> {
            Optional<UrnE> maybeDevUrn = urnRepo.findByUrn(d.getUrn());
            if (maybeDevUrn.isPresent()) {
                UrnE deviceUrn = maybeDevUrn.get();

                d.getIfces().forEach(i -> {
                    Optional<UrnE> maybeIfceUrn = urnRepo.findByUrn(i.getUrn());
                    if (maybeIfceUrn.isPresent()) {
                        UrnE ifceUrn = maybeIfceUrn.get();
                        UrnAdjcyE azAdjcy = UrnAdjcyE.builder()
                                .a(deviceUrn)
                                .z(ifceUrn)
                                .metrics(new HashMap<>())
                                .build();

                        UrnAdjcyE zaAdjcy = UrnAdjcyE.builder()
                                .a(ifceUrn)
                                .z(deviceUrn)
                                .metrics(new HashMap<>())
                                .build();

                        azAdjcy.getMetrics().put(Layer.INTERNAL, 1L);
                        zaAdjcy.getMetrics().put(Layer.INTERNAL, 1L);
                        adjcies.add(azAdjcy);
                        adjcies.add(zaAdjcy);

                    }
                });

            }

        });

        return adjcies;
    }

    private List<UrnE> urnsFromDevices(List<Device> devices) {
        List<UrnE> urns = new ArrayList<>();

        devices.forEach(d -> {
            UrnE deviceUrn = UrnE.builder()
                    .valid(true)
                    .urn(d.getUrn())
                    .deviceModel(d.getModel())
                    .deviceType(d.getType())
                    .urnType(UrnType.DEVICE)
                    .capabilities(d.getCapabilities())
                    .build();

            ReservableBandwidthE drbw = ReservableBandwidthE.builder()
                    .bandwidth(Integer.MAX_VALUE)
                    .ingressBw(Integer.MAX_VALUE)
                    .egressBw(Integer.MAX_VALUE)
                    .urn(deviceUrn)
                    .build();
            deviceUrn.setReservableBandwidth(drbw);

            if (null != d.getReservableVlans() && !d.getReservableVlans().isEmpty()) {

                ReservableVlanE resvVlan = ReservableVlanE.builder()
                        .vlanRanges(d.getReservableVlans())
                        .urn(deviceUrn)
                        .build();
                deviceUrn.setReservableVlans(resvVlan);
            }


            urns.add(deviceUrn);


            d.getIfces().forEach(i -> {
                UrnE ifceUrn = UrnE.builder()
                        .valid(true)
                        .urn(i.getUrn())
                        .urnType(UrnType.IFCE)
                        .capabilities(i.getCapabilities())
                        .ifceType(IfceType.PORT)
                        .build();

                if (null != i.getReservableBw()) {
                    ReservableBandwidthE irbw = ReservableBandwidthE.builder()
                            .bandwidth(i.getReservableBw())
                            .ingressBw(i.getReservableBw())
                            .egressBw(i.getReservableBw())
                            .urn(ifceUrn)
                            .build();
                    ifceUrn.setReservableBandwidth(irbw);
                }

                if (null != i.getReservableVlans() && !i.getReservableVlans().isEmpty()) {
                    ReservableVlanE resvVlan = ReservableVlanE.builder()
                            .vlanRanges(i.getReservableVlans())
                            .urn(ifceUrn)
                            .build();
                    ifceUrn.setReservableVlans(resvVlan);
                }
                urns.add(ifceUrn);
            });
        });

        return urns;

    }


    private List<Device> importDevicesFromFile(String filename, boolean overwrite) throws IOException {
        File jsonFile = new File(filename);
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(jsonFile, Device[].class));
    }

    private List<UrnAdjcyE> importAdjciesFromFile(String filename, boolean overwrite) throws IOException {
        File jsonFile = new File(filename);
        ObjectMapper mapper = new ObjectMapper();
        List<UrnAdjcy> fromFile = Arrays.asList(mapper.readValue(jsonFile, UrnAdjcy[].class));
        List<UrnAdjcyE> result = new ArrayList<>();
        fromFile.forEach(t -> {
            Optional<UrnE> maybeA = urnRepo.findByUrn(t.getA());
            Optional<UrnE> maybeZ = urnRepo.findByUrn(t.getZ());
            if (maybeA.isPresent() && maybeZ.isPresent()) {
                UrnE a = maybeA.get();
                UrnE z = maybeZ.get();
                Map<Layer, Long> metrics = t.getMetrics();
                UrnAdjcyE adjcy = UrnAdjcyE.builder().a(a).z(z).metrics(metrics).build();
                result.add(adjcy);
            }
        });
        return result;

    }

}
