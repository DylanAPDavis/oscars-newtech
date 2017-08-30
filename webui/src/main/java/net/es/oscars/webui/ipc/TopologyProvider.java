package net.es.oscars.webui.ipc;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.rsrc.ReservableBandwidth;
import net.es.oscars.dto.topo.DevicePortMap;
import net.es.oscars.dto.topo.Topology;
import net.es.oscars.dto.viz.DevicePositions;
import net.es.oscars.dto.viz.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TopologyProvider {
    @Autowired
    private RestTemplate restTemplate;

    private Topology topology = null;
    private Map<String, Position> positions = null;
    private Map<String, Set<String>> devicePortMap = null;

    private final String oscarsUrl = "https://localhost:8000";

    public Topology getTopology() {
        if (topology == null) {
            String restPath = oscarsUrl + "/topo/multilayer";
            topology = restTemplate.getForObject(restPath, Topology.class);
        }

        return topology;
    }

    public Map<String, Set<String>> devicePortMap() {
        if (devicePortMap == null) {
            devicePortMap = new HashMap<>();
            String restPath = oscarsUrl + "/topo/device_port_map";
            DevicePortMap dpm = restTemplate.getForObject(restPath, DevicePortMap.class);
            devicePortMap = dpm.getMap();
        }
        return devicePortMap;
    }

    // Reverse of devicePortMap: Key = port, Value = corresponding device
    public Map<String, String> portDeviceMap() {
        Map<String, String> p2d = new HashMap<>();
        Map<String, Set<String>> d2p = devicePortMap();

        for (String d : d2p.keySet()) {
            for (String p : d2p.get(d)) {
                p2d.put(p, d);
            }
        }

        return p2d;
    }

    public Map<String, Set<String>> getHubs() {
        Map<String, Set<String>> result = new HashMap<>();
        return result;
    }

    public Map<String, Position> getPositions() {
        if (positions == null) {
            positions = new HashMap<>();
            String restPath = oscarsUrl + "/ui/positions";
            DevicePositions rcvd = restTemplate.getForObject(restPath, DevicePositions.class);

            rcvd.getPositions().keySet().forEach(d -> {
                Integer x = rcvd.getPositions().get(d).getX();
                Integer y = rcvd.getPositions().get(d).getY();
                Position p = Position.builder().x(x).y(y).build();
                positions.put(d, p);
            });
        }

        return positions;
    }

    public List<ReservableBandwidth> getPortCapacities() {
        String restPath = oscarsUrl + "/topo/allport/bwcapacity";

        ReservableBandwidth[] portBW = restTemplate.getForObject(restPath, ReservableBandwidth[].class);

        return Arrays.asList(portBW);
    }


    public Integer computeLinkCapacity(String portA, String portZ, List<ReservableBandwidth> portCapacities) {
        // Compute link capacities from port capacities //
        List<ReservableBandwidth> portCaps = portCapacities.stream()
                .filter(p -> p.getTopoVertexUrn().equals(portA) || p.getTopoVertexUrn().equals(portZ))
                .collect(Collectors.toList());

        assert (portCaps.size() == 2);

        ReservableBandwidth bw1 = portCaps.get(0);
        ReservableBandwidth bw2 = portCaps.get(1);
        Integer aCapIn;
        Integer aCapEg;
        Integer zCapIn;
        Integer zCapEg;

        Integer minCap;

        if (bw1.getTopoVertexUrn().equals(portA)) {
            aCapIn = bw1.getIngressBw();
            aCapEg = bw1.getEgressBw();
            zCapIn = bw2.getIngressBw();
            zCapEg = bw2.getEgressBw();
        } else {
            aCapIn = bw2.getIngressBw();
            aCapEg = bw2.getEgressBw();
            zCapIn = bw1.getIngressBw();
            zCapEg = bw1.getEgressBw();
        }

        Set<Integer> bwCapSet = new HashSet<>();
        bwCapSet.add(aCapIn);
        bwCapSet.add(aCapEg);
        bwCapSet.add(zCapIn);
        bwCapSet.add(zCapEg);

        minCap = bwCapSet.stream().min(Integer::compare).get();

        return minCap;
    }


}
