package net.es.oscars.resv.rest;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.pss.EthPipeType;
import net.es.oscars.dto.resv.Connection;
import net.es.oscars.dto.resv.ConnectionFilter;
import net.es.oscars.dto.spec.PalindromicType;
import net.es.oscars.dto.spec.RequestedVlanFlow;
import net.es.oscars.dto.spec.RequestedVlanPipe;
import net.es.oscars.dto.spec.SurvivabilityType;
import net.es.oscars.pce.exc.DuplicateConnectionIdException;
import net.es.oscars.pce.exc.InvalidUrnException;
import net.es.oscars.pce.exc.PCEException;
import net.es.oscars.pce.exc.VlanNotFoundException;
import net.es.oscars.pss.PSSException;
import net.es.oscars.resv.ent.ConnectionE;
import net.es.oscars.resv.ent.RequestedVlanFixtureE;
import net.es.oscars.resv.ent.RequestedVlanJunctionE;
import net.es.oscars.resv.ent.RequestedVlanPipeE;
import net.es.oscars.resv.svc.ResvService;
import net.es.oscars.st.resv.ResvState;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ResvController {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ResvController(ResvService resvService) {
        this.resvService = resvService;
    }


    private ResvService resvService;


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException(NoSuchElementException ex) {
        // LOG.warn("user requested a strResource which didn't exist", ex);
    }


    @ExceptionHandler(VlanNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "VLAN not available")
    @ResponseBody
    public Map<String, Object> handleNoVlanFound(VlanNotFoundException ex) {
        String message = "Requested VLANs not available at these URNs: " + StringUtils.join(ex.getBadUrns(), ",");
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", true);
        result.put("error_message", message);
        log.error(message);
        return result;
    }

    @ExceptionHandler(DuplicateConnectionIdException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate connection id")
    @ResponseBody
    public Map<String, Object> handleDuplicateConnId(DuplicateConnectionIdException ex) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", true);
        result.put("error_message", ex.getMessage());
        log.error(ex.getMessage());
        return result;
    }

    @ExceptionHandler(InvalidUrnException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, Object> handleInvalidUrn(InvalidUrnException ex) {
        String message = "One or more requested URNs not found: " + StringUtils.join(ex.getBadUrns(), ",");

        HashMap<String, Object> result = new HashMap<>();
        result.put("error", true);
        result.put("error_message", message);
        log.error(message);
        return result;
    }

    @RequestMapping(value = "/resv/exists/{connectionId}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean exists(@PathVariable("connectionId") String connectionId) {
        log.info("checking if " + connectionId+" exists");
        Optional<ConnectionE> connE = resvService.findByConnectionId(connectionId);
        return connE.isPresent();

    }

    @RequestMapping(value = "/resv/get/{connectionId}", method = RequestMethod.GET)
    @ResponseBody
    public Connection getResv(@PathVariable("connectionId") String connectionId) {
        log.info("retrieving " + connectionId);

        return convertConnToDto(resvService.findByConnectionId(connectionId).orElseThrow(NoSuchElementException::new));

    }

    // TODO: make better
    @RequestMapping(value = "/resv/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Connection> allResvs() {

        log.info("listing all resvs");
        List<Connection> dtoItems = new ArrayList<>();

        for (ConnectionE eItem : resvService.findAll()) {
            Connection dtoItem = convertConnToDto(eItem);
            dtoItems.add(dtoItem);
        }
        return dtoItems;

    }


    @RequestMapping(value = "/resv/filter", method = RequestMethod.POST)
    @ResponseBody
    public Set<Connection> resvFilter(@RequestBody ConnectionFilter filter) {

        List<ConnectionE> allConnections = resvService.findAll();
        Set<Connection> result = new HashSet<>();;

        // No Filters specified
        if(filter.getNumFilters() == 0)
        {
            ;
        }
        else
        {
            /* Filter by exact matches */
            // User Name
            if(!filter.getUserNames().isEmpty())
            {
                allConnections = allConnections.stream()
                        .filter(c -> filter.getUserNames().contains(c.getSpecification().getUsername()))
                        .collect(Collectors.toList());
            }

            // Resv State
            if(!filter.getResvStates().isEmpty())
            {
                allConnections = allConnections.stream()
                        .filter(c -> filter.getResvStates().contains(c.getStates().getResv()))
                        .collect(Collectors.toList());
            }

            // Oper State
            if(!filter.getOperStates().isEmpty())
            {
                allConnections = allConnections.stream()
                        .filter(c -> filter.getOperStates().contains(c.getStates().getOper()))
                        .collect(Collectors.toList());
            }

            // Prov State
            if(!filter.getProvStates().isEmpty())
            {
                allConnections = allConnections.stream()
                        .filter(c -> filter.getProvStates().contains(c.getStates().getProv()))
                        .collect(Collectors.toList());
            }

            // Connection ID
            if(!filter.getConnectionIds().isEmpty())
            {
                allConnections = allConnections.stream()
                        .filter(c -> filter.getConnectionIds().contains(c.getConnectionId()))
                        .collect(Collectors.toList());
            }


            /* Filter by range values */
            Set<Date> allStartDates = filter.getStartDates();
            Set<Date> allEndDates = filter.getEndDates();
            Set<Integer> allMinBWs = filter.getMinBandwidths();
            Set<Integer> allMaxBWs = filter.getMaxBandwidths();

            List<ConnectionE> connstoRemove = new ArrayList<>();

            // Start Dates
            if(!allStartDates.isEmpty())
            {
                Date earliestStart = allStartDates.stream().findFirst().get();

                for(Date oneStartDate : allStartDates)
                {
                    if(oneStartDate.before(earliestStart))
                        earliestStart = oneStartDate;
                }

                for(ConnectionE c : allConnections)
                {
                    if(earliestStart.after(c.getSpecification().getScheduleSpec().getStartDates().get(0)))  // Only checks the first requested Start Date.
                        connstoRemove.add(c);
                }
            }

            // End Dates
            if(!allEndDates.isEmpty())
            {
                Date latestEnd = allEndDates.stream().findFirst().get();

                for(Date oneEndDate : allEndDates)
                {
                    if(oneEndDate.after(latestEnd))
                        latestEnd = oneEndDate;
                }

                for(ConnectionE c : allConnections)
                {
                    if(latestEnd.before(c.getSpecification().getScheduleSpec().getStartDates().get(0)))  // Only checks the first requested End Date.
                        connstoRemove.add(c);
                }
            }

            // Min/Max Bandwidth
            Integer smallestMin = Integer.MAX_VALUE;
            Integer largestMax = Integer.MIN_VALUE;

            if(!allMinBWs.isEmpty())
            {
                for(Integer oneMin : allMinBWs)
                {
                    if(oneMin < smallestMin)
                        smallestMin = oneMin;
                }
            }

            if(!allMaxBWs.isEmpty())
            {
                for(Integer oneMax : allMaxBWs)
                {
                    if(oneMax > largestMax)
                        largestMax = oneMax;
                }
            }

            for(ConnectionE c : allConnections)
            {
                Set<RequestedVlanPipeE> allRequestedPipes = c.getSpecification().getRequested().getVlanFlow().getPipes();
                Set<RequestedVlanJunctionE> allRequestedJunctions = c.getSpecification().getRequested().getVlanFlow().getJunctions();

                Integer largestRequested = 0;
                Integer smallestRequested = Integer.MAX_VALUE;

                for(RequestedVlanPipeE onePipe : allRequestedPipes)
                {
                    if(onePipe.getAzMbps() > largestRequested)
                        largestRequested = onePipe.getAzMbps();

                    if(onePipe.getZaMbps() > largestRequested)
                        largestRequested = onePipe.getZaMbps();

                    if(onePipe.getAzMbps() < smallestRequested)
                        smallestRequested = onePipe.getAzMbps();

                    if(onePipe.getZaMbps() < smallestRequested)
                        smallestRequested = onePipe.getZaMbps();
                }

                for(RequestedVlanJunctionE oneJunc : allRequestedJunctions)
                {
                    Set<RequestedVlanFixtureE> theRequestedFixtures = oneJunc.getFixtures();

                    for(RequestedVlanFixtureE oneFix : theRequestedFixtures)
                    {
                        if(oneFix.getInMbps() > largestRequested)
                            largestRequested = oneFix.getInMbps();

                        if(oneFix.getEgMbps() > largestRequested)
                            largestRequested = oneFix.getEgMbps();

                        if(oneFix.getInMbps() < smallestRequested)
                            smallestRequested = oneFix.getInMbps();

                        if(oneFix.getEgMbps() < smallestRequested)
                            smallestRequested = oneFix.getEgMbps();

                    }
                }

                Integer correctedMin = smallestMin;
                Integer correctedMax = largestMax;

                if(smallestMin == Integer.MAX_VALUE)
                    correctedMin = Integer.MIN_VALUE;

                if(largestMax == Integer.MIN_VALUE)
                    correctedMax = Integer.MAX_VALUE;

                if(!(correctedMin <= largestRequested && correctedMax >= smallestRequested))
                    connstoRemove.add(c);
            }

            allConnections.removeAll(connstoRemove);
        }

        for(ConnectionE oneConnectionE : allConnections)
        {
            Connection oneConnDTO = convertConnToDto(oneConnectionE);
            result.add(oneConnDTO);
        }

        return result;
    }

    // Default endpoint for holding a new connection
    @RequestMapping(value = "/resv/connection/add", method = RequestMethod.POST)
    @ResponseBody
    public Connection submitConnection(@RequestBody Connection connection) throws PSSException, PCEException {
        log.info("Submitting a new complicated connection request");
        log.info(connection.toString());

        return holdConnection(connection);
    }

    // Endpoint for pre-check on a connection
    @RequestMapping(value = "/resv/connection/precheck", method = RequestMethod.POST)
    @ResponseBody
    public Connection preCheck(@RequestBody Connection connection) throws PSSException, PCEException {
        log.info("Pre-check initialized for ConnectionID: " + connection.getConnectionId());

        return preCheckConnection(connection); // may be null
    }


    @RequestMapping(value = "/resv/commit/{connectionId}", method = RequestMethod.GET)
    @ResponseBody
    public Connection commit(@PathVariable("connectionId") String connectionId) {
        log.info("attempting to commit " + connectionId);
        ConnectionE connE = resvService.findByConnectionId(connectionId).orElseThrow(NoSuchElementException::new);
        if (connE.getStates().getResv().equals(ResvState.HELD)) {
            connE.getStates().setResv(ResvState.COMMITTING);
            resvService.save(connE);
        }

        return this.convertConnToDto(connE);
    }


    @RequestMapping(value = "/resv/abort/{connectionId}", method = RequestMethod.GET)
    @ResponseBody
    public Connection abort(@PathVariable("connectionId") String connectionId) {
        log.info("attempting to commit " + connectionId);
        ConnectionE connE = resvService.findByConnectionId(connectionId).orElseThrow(NoSuchElementException::new);
        if (connE.getStates().getResv().equals(ResvState.HELD)) {
            connE.getStates().setResv(ResvState.ABORTING);
            resvService.abort(connE);
        }

        return this.convertConnToDto(connE);

    }

    private Connection holdConnection(Connection connection) throws PCEException, PSSException {
        connection = defineDefaults(connection);
        ConnectionE connE = modelMapper.map(connection, ConnectionE.class);

        resvService.hold(connE);

        log.info("saved connection, connectionId " + connection.getConnectionId());
        log.info(connE.toString());


        Connection conn = modelMapper.map(connE, Connection.class);
        log.info(conn.toString());


        return conn;

    }

    private Connection defineDefaults(Connection connection) {
        RequestedVlanFlow flow = connection.getSpecification().getRequested().getVlanFlow();
        if (flow.getMinPipes() == null) {
            flow.setMinPipes(flow.getPipes().size());
        }
        if (flow.getMaxPipes() == null) {
            flow.setMaxPipes(flow.getPipes().size());
        }

        Set<RequestedVlanPipe> pipes = flow.getPipes();
        for (RequestedVlanPipe pipe : pipes) {
            if (pipe.getAzMbps() == null) {
                pipe.setAzMbps(0);
            }
            if (pipe.getZaMbps() == null) {
                pipe.setZaMbps(0);
            }
            if (pipe.getAzERO() == null) {
                pipe.setAzERO(new ArrayList<>());
            }
            if (pipe.getZaERO() == null) {
                pipe.setZaERO(new ArrayList<>());
            }
            if (pipe.getUrnBlacklist() == null) {
                pipe.setUrnBlacklist(new HashSet<>());
            }
            if (pipe.getPipeType() == null) {
                pipe.setPipeType(EthPipeType.REQUESTED);
            }
            if (pipe.getEroPalindromic() == null) {
                pipe.setEroPalindromic(PalindromicType.PALINDROME);
            }
            if (pipe.getEroSurvivability() == null) {
                pipe.setEroSurvivability(SurvivabilityType.SURVIVABILITY_NONE);
            }
            if (pipe.getNumPaths() == null) {
                pipe.setNumPaths(1);
            }
            if (pipe.getPriority() == null) {
                pipe.setPriority(Integer.MAX_VALUE);
            }
        }
        flow.setPipes(pipes);
        connection.getSpecification().getRequested().setVlanFlow(flow);
        return connection;
    }

    private Connection preCheckConnection(Connection connection) throws PCEException, PSSException {
        log.info("Pre-checking ConnectionID: " + connection.getConnectionId());
        connection = defineDefaults(connection);
        ConnectionE connE = modelMapper.map(connection, ConnectionE.class);

        Boolean successful = resvService.preCheck(connE);

        Connection conn = modelMapper.map(connE, Connection.class);

        if (successful) {
            log.info("Pre-check result: SUCCESS");
            return conn;
        } else {
            log.info("Pre-check result: UNSUCCESSFUL");
            return null;
        }
    }

    private Connection convertConnToDto(ConnectionE connectionE) {
        return modelMapper.map(connectionE, Connection.class);
    }

}