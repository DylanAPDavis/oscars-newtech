package net.es.oscars.webui.cont;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityRequest;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityResponse;
import net.es.oscars.dto.rsrc.ReservableBandwidth;
import net.es.oscars.dto.spec.ReservedBandwidth;
import net.es.oscars.dto.topo.ReservedBandwidths;
import net.es.oscars.webui.dto.MinimalBwAvailRequest;
import net.es.oscars.webui.ipc.TopologyProvider;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class TopologyController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TopologyProvider topologyProvider;

    private final String oscarsUrl = "https://localhost:8000";

    @RequestMapping(value = "/topology/reservedbw", method = RequestMethod.GET)
    @ResponseBody
    public List<ReservedBandwidth> getAllReservedBw() {
        String restPath = oscarsUrl + "/topo/allreservedbw";
        ReservedBandwidths rbs = restTemplate.getForObject(restPath, ReservedBandwidths.class);

        return rbs.getBandwidths();

    }

    @RequestMapping(value = "/topology/reservedbw", method = RequestMethod.POST)
    @ResponseBody
    public List<ReservedBandwidth> get_reserved_bw(@RequestBody List<String> resUrns) {
        String restPath = oscarsUrl + "/topo/reservedbw";

        if (resUrns == null) {
            resUrns = new ArrayList<>();
        }
        ReservedBandwidths rbs = restTemplate.postForObject(restPath, resUrns, ReservedBandwidths.class);
        return rbs.getBandwidths();

    }

    @RequestMapping(value = "/topology/bwcapacity", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> getAllPortCapacity() {
        List<ReservableBandwidth> bwCapList = topologyProvider.getPortCapacities();

        return bwCapList
                .stream()
                .collect(Collectors.toMap(ReservableBandwidth::getTopoVertexUrn,
                        bw -> Math.min(bw.getIngressBw(), bw.getEgressBw())));
    }

    @RequestMapping(value = "/topology/bwcapacity", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> get_port_capacity(@RequestBody List<String> ports) {
        Map<String, Integer> urn2CapMap = new HashMap<>();

        List<ReservableBandwidth> bwCapList = topologyProvider.getPortCapacities();

        bwCapList = bwCapList.stream()
                .filter(bwCap -> ports.contains(bwCap.getTopoVertexUrn()))
                .collect(Collectors.toList());

        for (ReservableBandwidth oneBW : bwCapList) {
            Integer minCap = Math.min(oneBW.getIngressBw(), oneBW.getEgressBw());
            urn2CapMap.put(oneBW.getTopoVertexUrn(), minCap);
        }

        return urn2CapMap;
    }


    @RequestMapping(value = "/topology/bwavailability/path", method = RequestMethod.POST)
    @ResponseBody
    public BandwidthAvailabilityResponse getBwAvailability(@RequestBody MinimalBwAvailRequest minReq) {
        String restPath = oscarsUrl + "/bwavail/path";

        List<List<String>> eroListAZ = new ArrayList<>();
        List<List<String>> eroListZA = new ArrayList<>();
        eroListAZ.add(minReq.getAzERO());
        eroListZA.add(minReq.getZaERO());

        Date startDate = new DateTime(minReq.getStartTime()).toDate();
        Date endDate = new DateTime(minReq.getEndTime()).toDate();

        BandwidthAvailabilityRequest bwRequest = new BandwidthAvailabilityRequest();
        bwRequest.setAzEros(eroListAZ);
        bwRequest.setZaEros(eroListZA);
        bwRequest.setMinAzBandwidth(minReq.getAzBandwidth());
        bwRequest.setMinZaBandwidth(minReq.getZaBandwidth());
        bwRequest.setStartDate(startDate);
        bwRequest.setEndDate(endDate);

        log.info("AZ EROs: " + bwRequest.getAzEros());
        log.info("ZA EROs: " + bwRequest.getZaEros());
        log.info("AZ B/W: " + bwRequest.getMinAzBandwidth());
        log.info("ZA B/W: " + bwRequest.getMinZaBandwidth());
        log.info("Start: " + bwRequest.getStartDate());
        log.info("End: " + bwRequest.getEndDate());

        return restTemplate.postForObject(restPath, bwRequest, BandwidthAvailabilityResponse.class);
    }


    @RequestMapping(value = "/topology/deviceportmap/full", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Set<String>> get_device2port_map() {

        return topologyProvider.devicePortMap();
    }


    @RequestMapping(value = "/topology/deviceportmap/{deviceURN}", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> get_single_port_set(@PathVariable String deviceURN) {
        Map<String, Set<String>> fullPortMap = topologyProvider.devicePortMap();

        Set<String> portMap = fullPortMap.get(deviceURN);

        if (portMap == null)
            portMap = new HashSet<>();

        return portMap;
    }

    @RequestMapping(value = "/topology/portdevicemap/full", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> get_port2device_map() {

        return topologyProvider.portDeviceMap();
    }
}
