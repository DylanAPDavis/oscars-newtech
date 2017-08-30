package net.es.oscars.web.rest;


import lombok.extern.slf4j.Slf4j;
import net.es.oscars.resv.beans.PeriodBandwidth;
import net.es.oscars.resv.db.FixtureRepository;
import net.es.oscars.resv.db.PipeRepository;
import net.es.oscars.resv.db.ScheduleRepository;
import net.es.oscars.resv.db.VlanRepository;
import net.es.oscars.resv.ent.*;
import net.es.oscars.resv.enums.BwDirection;
import net.es.oscars.resv.enums.Phase;
import net.es.oscars.resv.svc.ResvLibrary;
import net.es.oscars.resv.svc.ResvService;
import net.es.oscars.topo.beans.IntRange;
import net.es.oscars.topo.beans.PortBwVlan;
import net.es.oscars.topo.db.DeviceRepository;
import net.es.oscars.topo.enums.Layer;
import net.es.oscars.topo.enums.UrnType;
import net.es.oscars.topo.svc.TopoService;
import net.es.oscars.web.beans.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class TopoController {

    @Autowired
    private TopoService topoService;

    @Autowired
    private ResvService resvService;

    @Autowired
    private DeviceRepository deviceRepo;


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException(NoSuchElementException ex) {
        log.warn("requested an item which did not exist", ex);
    }



    @RequestMapping(value = "/api/topo/ethernetPortsByDevice", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> ethernetPortsByDevice() {
        Map<String, List<String>> result = new HashMap<>();
        deviceRepo.findAll().forEach(d -> {
            List<String> ports = new ArrayList<>();
            d.getPorts().forEach(p -> {
                if (p.getCapabilities().contains(Layer.ETHERNET)) {
                    ports.add(p.getUrn());
                }
            });
            result.put(d.getUrn(), ports);

        });
        return result;
    }


    @RequestMapping(value = "/api/topo/baseline", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, PortBwVlan> baseline() {

        // grab everything available
        return ResvLibrary.portBwVlans(topoService.getTopoUrnMap(), new HashSet<>(), new HashMap<>(), new HashMap<>());

    }


    @RequestMapping(value = "/api/topo/available", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, PortBwVlan> available(@RequestBody Interval interval) {

        return resvService.available(interval);

    }


}