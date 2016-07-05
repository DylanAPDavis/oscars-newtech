package net.es.oscars.helpers;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.pss.EthFixtureType;
import net.es.oscars.dto.pss.EthJunctionType;
import net.es.oscars.dto.pss.EthPipeType;
import net.es.oscars.resv.ent.*;
import net.es.oscars.topo.beans.TopoEdge;
import net.es.oscars.topo.beans.TopoVertex;
import net.es.oscars.topo.beans.Topology;
import net.es.oscars.topo.dao.UrnAdjcyRepository;
import net.es.oscars.topo.dao.UrnRepository;
import net.es.oscars.topo.ent.*;
import net.es.oscars.topo.enums.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TestEntityBuilder {

    @Autowired
    UrnRepository urnRepo;

    @Autowired
    UrnAdjcyRepository adjcyRepo;


    public void populateRepos(Collection<TopoVertex> vertices, Collection<TopoEdge> edges, Map<TopoVertex,TopoVertex> portToDeviceMap){
        urnRepo.deleteAll();
        adjcyRepo.deleteAll();

        List<UrnE> urnList = new ArrayList<>();
        List<UrnAdjcyE> adjcyList = new ArrayList<>();
        for(TopoEdge edge : edges){
            TopoVertex a = edge.getA();

            TopoVertex z = edge.getZ();

            UrnE aUrn = findOrMakeUrn(a, urnList, portToDeviceMap);
            urnList.add(aUrn);

            UrnE zUrn = findOrMakeUrn(z, urnList, portToDeviceMap);
            urnList.add(zUrn);

            UrnAdjcyE adj = buildUrnAdjcy(edge, aUrn, zUrn);
            adjcyList.add(adj);
        }
        urnRepo.save(urnList);
        adjcyRepo.save(adjcyList);
    }

    public Topology buildTopology(List<String> nodeNames, Map<String, VertexType> typeMap,
                                  Map<String, List<String>> neighborMap, Layer layer, Long metric){
        Topology topo = new Topology();
        Set<TopoVertex> vertices = new HashSet<>();
        Set<TopoEdge> edges = new HashSet<>();
        for(String name : nodeNames){
            List<String> neighbors = neighborMap.get(name);
            TopoVertex thisVertex = buildTopoVertex(name, typeMap.get(name));
            vertices.add(thisVertex);
            for(String neighborName : neighbors){
                TopoVertex neighborVertex = getFromSet(neighborName, vertices);
                if(neighborVertex == null){
                    neighborVertex = buildTopoVertex(neighborName, typeMap.get(neighborName));
                    vertices.add(neighborVertex);
                }
                TopoEdge edge = buildTopoEdge(thisVertex, neighborVertex, layer, metric);
                edges.add(edge);
            }
        }
        topo.setLayer(layer);
        topo.setVertices(vertices);
        topo.setEdges(edges);
        return topo;
    }

    public RequestedBlueprintE buildRequest(String deviceName, Set<String> fixtureNames,
                                            Integer azMbps, Integer zaMbps, String vlanExp){

        Set<RequestedVlanJunctionE> junctions = new HashSet<>();
        RequestedVlanJunctionE junction = buildRequestedJunction(deviceName, fixtureNames, azMbps, zaMbps, vlanExp);
        junctions.add(junction);

        Set<RequestedVlanFlowE> vlanFlows = new HashSet<>();
        RequestedVlanFlowE flow = RequestedVlanFlowE.builder()
                .junctions(junctions)
                .pipes(new HashSet<>())
                .build();
        vlanFlows.add(flow);

        return RequestedBlueprintE.builder()
                .vlanFlows(vlanFlows)
                .layer3Flows(new HashSet<>())
                .build();
    }

    public RequestedBlueprintE buildRequest(String aPort, String aDevice, String zPort, String zDevice,
                                            Integer azMbps, Integer zaMbps, Boolean palindromic, String vlanExp){

        Set<RequestedVlanPipeE> pipes = new HashSet<>();
        RequestedVlanPipeE pipe = buildRequestedPipe(aPort, aDevice, zPort, zDevice, azMbps, zaMbps, palindromic, vlanExp);
        pipes.add(pipe);

        Set<RequestedVlanFlowE> vlanFlows = new HashSet<>();
        RequestedVlanFlowE flow = RequestedVlanFlowE.builder()
                .junctions(new HashSet<>())
                .pipes(pipes)
                .build();
        vlanFlows.add(flow);

        return RequestedBlueprintE.builder()
                .vlanFlows(vlanFlows)
                .layer3Flows(new HashSet<>())
                .build();

    }

    public ScheduleSpecificationE buildSchedule(Date start, Date end){
        return ScheduleSpecificationE.builder()
                .notAfter(start)
                .notBefore(end)
                .durationMinutes(Duration.between(start.toInstant(), end.toInstant()).toMinutes())
                .build();
    }

    public UrnE findOrMakeUrn(TopoVertex v, List<UrnE> urnList, Map<TopoVertex,TopoVertex> portToDeviceMap){
        UrnE urn = getFromUrnList(v.getUrn(), urnList);
        if(urn == null){
            if (!v.getVertexType().equals(VertexType.PORT)) {
                urn = buildUrn(v, null);
            } else {
                TopoVertex deviceVertex = portToDeviceMap.get(v);
                urn = buildUrn(v, determineDeviceModel(deviceVertex.getVertexType()));
            }
        }
        return urn;
    }

    public UrnE buildUrn(TopoVertex vertex, DeviceModel parentModel){
        VertexType vertexType = vertex.getVertexType();
        UrnType urnType = determineUrnType(vertexType);
        DeviceType deviceType = determineDeviceType(vertexType);
        IfceType ifceType = determineIfceType(vertexType);
        DeviceModel model = parentModel == null ? determineDeviceModel(vertexType) : parentModel;

        UrnE urn =  UrnE.builder()
                .urn(vertex.getUrn())
                .urnType(urnType)
                .deviceType(deviceType)
                .ifceType(ifceType)
                .deviceModel(model)
                .reservableBandwidth(null)
                .reservableVlans(null)
                .reservablePssResources(new HashSet<>())
                .valid(true)
                .build();

        Integer ingressBw = 1000;
        Integer egressBw = 1000;
        List<Integer> floors = Arrays.asList(1, 10, 20, 30);
        List<Integer> ceilings = Arrays.asList(9, 19, 29, 39);
        if(vertexType.equals(VertexType.PORT)){
            ReservableBandwidthE resvBw = buildReservableBandwidth(urn, ingressBw, egressBw);
            ReservableVlanE resvVlan = buildReservableVlan(urn, buildIntRanges(floors, ceilings));
            urn.setReservableBandwidth(resvBw);
            urn.setReservableVlans(resvVlan);
        }
        return urn;
    }

    public UrnAdjcyE buildUrnAdjcy(TopoEdge edge, UrnE a, UrnE z){
        HashMap<Layer, Long> metrics = new HashMap<>();
        metrics.put(edge.getLayer(), edge.getMetric());
        return UrnAdjcyE.builder()
                .a(a)
                .z(z)
                .metrics(metrics)
                .build();
    }


    public RequestedVlanPipeE buildRequestedPipe(String aPort, String aDevice, String zPort, String zDevice,
                                                 Integer azMbps, Integer zaMbps, Boolean palindromic, String vlanExp){

        Set<String> aFixNames = new HashSet<>();
        aFixNames.add(aPort);

        Set<String> zFixNames = new HashSet<>();
        zFixNames.add(zPort);

        return RequestedVlanPipeE.builder()
                .aJunction(buildRequestedJunction(aDevice, aFixNames, azMbps, zaMbps, vlanExp))
                .zJunction(buildRequestedJunction(zDevice, zFixNames, azMbps, zaMbps, vlanExp))
                .pipeType(EthPipeType.REQUESTED)
                .azERO(new ArrayList<>())
                .zaERO(new ArrayList<>())
                .azMbps(azMbps)
                .zaMbps(zaMbps)
                .eroPalindromic(palindromic)
                .build();
    }

    public RequestedVlanJunctionE buildRequestedJunction(String deviceName, Set<String> fixtureNames,
                                                         Integer azMbps, Integer zaMbps, String vlanExp){

        Optional<UrnE> optUrn = urnRepo.findByUrn(deviceName);

        Set<RequestedVlanFixtureE> fixtures = fixtureNames
                .stream()
                .map(fixName -> buildRequestedFixture(fixName, azMbps, zaMbps, vlanExp))
                .collect(Collectors.toSet());

        return RequestedVlanJunctionE.builder()
                .deviceUrn(optUrn.isPresent() ? optUrn.get() : null)
                .fixtures(fixtures)
                .junctionType(EthJunctionType.REQUESTED)
                .build();
    }

    public RequestedVlanFixtureE buildRequestedFixture(String fixName, Integer azMbps, Integer zaMbps,
                                                       String vlanExp){

        Optional<UrnE> optUrn = urnRepo.findByUrn(fixName);

        return RequestedVlanFixtureE.builder()
                .portUrn(optUrn.isPresent() ? optUrn.get() : null)
                .fixtureType(EthFixtureType.REQUESTED)
                .inMbps(azMbps)
                .egMbps(zaMbps)
                .vlanExpression(vlanExp)
                .build();
    }


    public TopoEdge buildTopoEdge(TopoVertex a, TopoVertex z, Layer layer, Long metric){
        return TopoEdge.builder()
                .a(a)
                .z(z)
                .layer(layer)
                .metric(metric)
                .build();
    }

    public TopoVertex buildTopoVertex(String name, VertexType type){
        return TopoVertex.builder()
                .vertexType(type)
                .urn(name)
                .build();
    }

    public Set<IntRangeE> buildIntRanges(List<Integer> floors, List<Integer> ceilings){
        Set<IntRangeE> ranges = new HashSet<>();
        for(int i = 0; i < floors.size(); i++){
            Integer floor = floors.get(i);
            Integer ceiling = ceilings.get(i);
            ranges.add(buildIntRange(floor, ceiling));
        }
        return ranges;
    }

    public IntRangeE buildIntRange(Integer floor, Integer ceiling){
        return IntRangeE.builder()
                .floor(floor)
                .ceiling(ceiling)
                .build();
    }

    public ReservableBandwidthE buildReservableBandwidth(UrnE urn, Integer azMbps, Integer zaMbps){
        return ReservableBandwidthE.builder()
                .urn(urn)
                .bandwidth(Math.max(azMbps, zaMbps))
                .egressBw(azMbps)
                .ingressBw(zaMbps)
                .build();
    }

    public ReservableVlanE buildReservableVlan(UrnE urn, Set<IntRangeE> ranges){
        return ReservableVlanE.builder()
                .urn(urn)
                .vlanRanges(ranges)
                .build();
    }

    public UrnType determineUrnType(VertexType type){
        switch(type){
            case ROUTER:
            case SWITCH: return UrnType.DEVICE;
            case PORT: return UrnType.IFCE;
            default: return UrnType.DEVICE;
        }
    }

    public DeviceType determineDeviceType(VertexType type){
        switch(type){
            case ROUTER: return DeviceType.ROUTER;
            case SWITCH: return DeviceType.SWITCH;
            default: return null;
        }
    }

    public IfceType determineIfceType(VertexType type){
        switch(type){
            case PORT: return IfceType.PORT;
            default: return null;
        }
    }

    public DeviceModel determineDeviceModel(VertexType type){
        switch(type){
            case ROUTER: return DeviceModel.JUNIPER_MX;
            case SWITCH: return DeviceModel.JUNIPER_EX;
            default: return null;
        }
    }

    public UrnE getFromUrnList(String name, List<UrnE> urns){
        Optional<UrnE> optUrn = urns.stream().filter(u -> u.getUrn().equals(name)).findFirst();
        if(optUrn.isPresent()){
            return optUrn.get();
        }
        else{
            return null;
        }
    }


    public TopoVertex getFromSet(String name, Set<TopoVertex> vertices){
        Optional<TopoVertex> optVertex = vertices.stream().filter(v -> v.getUrn().equals(name)).findFirst();
        if(optVertex.isPresent()){
            return optVertex.get();
        }
        return null;
    }
}
