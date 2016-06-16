package net.es.oscars.pce;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.IntRange;
import net.es.oscars.resv.ent.RequestedVlanFixtureE;
import net.es.oscars.resv.ent.RequestedVlanJunctionE;
import net.es.oscars.resv.ent.RequestedVlanPipeE;
import net.es.oscars.topo.beans.TopoEdge;
import net.es.oscars.topo.beans.Topology;
import net.es.oscars.topo.dao.UrnRepository;
import net.es.oscars.topo.ent.IntRangeE;
import net.es.oscars.topo.ent.ReservableBandwidthE;
import net.es.oscars.topo.ent.ReservableVlanE;
import net.es.oscars.topo.ent.UrnE;
import net.es.oscars.topo.enums.Layer;
import net.es.oscars.topo.svc.TopoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Component
public class PruningService {

    @Autowired
    private UrnRepository urnRepo;


    public Topology pruneWithBwVlans(Topology topo, Integer Bw, Set<Integer> vlans, List<UrnE> urns){
        return pruneTopology(topo, Bw, Bw, vlans, urns);
    }

    public Topology pruneWithBwVlans(Topology topo, Integer Bw, Set<Integer> vlans){
        return pruneTopology(topo, Bw, Bw, vlans, urnRepo.findAll());
    }

    public Topology pruneWithAZBwVlans(Topology topo, Integer azBw, Integer zaBw, Set<Integer> vlans, List<UrnE> urns){
        return pruneTopology(topo, azBw, zaBw, vlans, urns);
    }

    public Topology pruneWithAZBwVlans(Topology topo, Integer azBw, Integer zaBw, Set<Integer> vlans){
        return pruneTopology(topo, azBw, zaBw, vlans, urnRepo.findAll());
    }

    public Topology pruneWithBw(Topology topo, Integer Bw, List<UrnE> urns){
        return pruneTopology(topo, Bw, Bw, null, urns);
    }

    public Topology pruneWithBw(Topology topo, Integer Bw){
        return pruneTopology(topo, Bw, Bw, null, urnRepo.findAll());
    }

    public Topology pruneWithAZBw(Topology topo, Integer azBw, Integer zaBw, List<UrnE> urns){
        return pruneTopology(topo, azBw, zaBw, null, urns);
    }

    public Topology pruneWithAZBw(Topology topo, Integer azBw, Integer zaBw){
        return pruneTopology(topo, azBw, zaBw, null, urnRepo.findAll());
    }

    public Topology pruneForPipe(Topology topo, RequestedVlanPipeE pipe){
        assert(pipe != null);
        assert(topo != null);
        assert(urnRepo != null);
        assert(urnRepo.findAll() != null);
        return pruneForPipe(topo, pipe, urnRepo.findAll());
    }

    public Topology pruneForPipe(Topology topo, RequestedVlanPipeE pipe, List<UrnE> urns){
        Integer azBw = pipe.getAzMbps();
        Integer zaBw = pipe.getZaMbps();
        Set<Integer> vlans = new HashSet<>();
        vlans.addAll(getVlansFromJunction(pipe.getAJunction()));
        vlans.addAll(getVlansFromJunction(pipe.getZJunction()));
        return pruneTopology(topo, azBw, zaBw, vlans, urns);
    }

    private Set<Integer> findOpenVlans(Set<TopoEdge> edges, List<UrnE> urns){
        //TODO: Figure out best way to find a VLAN that will work across all edges
        // Note: MPLS edges can use any available VLAN

        TopoEdge e = edges.iterator().next();
        Set<IntRange> aRanges = getVlanRangesFromUrn(urns, e.getA().getUrn());
        Set<IntRange> zRanges = getVlanRangesFromUrn(urns, e.getZ().getUrn());
        Set<Integer> overlap = new HashSet<>();
        overlap = addToOverlap(overlap, aRanges);
        overlap = addToOverlap(overlap, zRanges);
        return new HashSet<Integer>();
    }

    private Set<Integer> addToOverlap(Set<Integer> overlap, Set<IntRange> ranges){
        for(IntRange range : ranges){
            Set<Integer> numbersInRange = getSetOfNumbersInRange(range);
            if(overlap.isEmpty()){
                overlap.addAll(numbersInRange);
            }else{
                overlap.retainAll(numbersInRange);
            }
        }
        return overlap;
    }

    private Set<Integer> getSetOfNumbersInRange(IntRange aRange) {
        Set<Integer> numbers = new HashSet<>();
        for(Integer num = aRange.getFloor(); num <= aRange.getCeiling(); num++){
            numbers.add(num);
        }
        return numbers;
    }

    private Set<Integer> getVlansFromJunction(RequestedVlanJunctionE junction){
        return junction.getFixtures().stream().map(RequestedVlanFixtureE::getVlanExpression)
                .map(Integer::parseInt).collect(Collectors.toSet());
    }

    private Topology pruneTopology(Topology topo, Integer azBw, Integer zaBw, Set<Integer> vlans, List<UrnE> urns){
        Topology pruned = new Topology();
        pruned.setLayer(topo.getLayer());
        pruned.setVertices(topo.getVertices());
        Set<TopoEdge> availableEdges = topo.getEdges().stream()
                .filter(e -> availableBW(e, azBw, zaBw, urns))
                .collect(Collectors.toSet());
        if(vlans == null){
            Set<Integer> openVlans = findOpenVlans(availableEdges, urns);
        }
        pruned.setEdges(availableEdges);
        return pruned;
    }

    private Set<IntRange> getVlanRangesFromUrn(List<UrnE> urns, String matchingUrn){
        return urns.stream()
                .filter(u -> u.getUrn().equals(matchingUrn))
                .map(UrnE::getReservableVlans)
                .map(ReservableVlanE::getVlanRanges)
                .flatMap(Collection::stream)
                .map(IntRangeE::toDtoIntRange)
                .collect(Collectors.toSet());
    }


    private boolean availableVlans(TopoEdge edge, Set<Integer> vlans, List<UrnE> urns) {
        Set<IntRange> aRanges = getVlanRangesFromUrn(urns, edge.getA().getUrn());
        Set<IntRange> zRanges = getVlanRangesFromUrn(urns, edge.getZ().getUrn());

        assert aRanges.size() <= 1 && zRanges.size() <= 1;
        if(aRanges.isEmpty() || zRanges.isEmpty()){
            return true;
        } else{

            //Edge is in MPLS
            //Any one VLAN tag (available on both ends of the edge) can work
            if(edge.getLayer().equals(Layer.MPLS)){
                for(IntRange aRange : aRanges){
                    for(IntRange zRange: zRanges){
                        if(checkVlanRangeOverlap(aRange, zRange)){
                            return true;
                        }
                    }
                }
                return false;
            }
            for(Integer requestedVlan : vlans){
                boolean aContainsVlan = aRanges.stream().anyMatch(vr -> vr.contains(requestedVlan));
                boolean zContainsVlan = zRanges.stream().anyMatch(vr -> vr.contains(requestedVlan));
                if(!aContainsVlan || !zContainsVlan){
                    return false;
                }
            }
            return true;
        }
    }

    private boolean checkVlanRangeOverlap(IntRange aRange, IntRange zRange) {
        return aRange.getFloor() <= zRange.getCeiling() && zRange.getFloor() <= aRange.getCeiling();
    }


    private boolean availableBW(TopoEdge edge, Integer azBw, Integer zaBw, List<UrnE> urns){
        String aUrn = edge.getA().getUrn();
        String zUrn = edge.getZ().getUrn();
        log.debug("checking if " + aUrn + " and " + zUrn + " have enough bandwidth ");
        List<ReservableBandwidthE> aMatching = urns.stream()
                .filter(u -> u.getUrn().equals(edge.getA().getUrn()))
                .map(UrnE::getReservableBandwidth)
                .collect(Collectors.toList());
        List<ReservableBandwidthE> zMatching = urns.stream()
                .filter(u -> u.getUrn().equals(edge.getZ().getUrn()))
                .map(UrnE::getReservableBandwidth)
                .collect(Collectors.toList());

        assert aMatching.size() <= 1 && zMatching.size() <=1;
        if (aMatching.isEmpty() || zMatching.isEmpty()) {
            log.info("bandwidth does not apply to " + aUrn + " or " + zUrn);
            return true;
        } else {
            return aMatching.get(0).getEgressBw() >= azBw && aMatching.get(0).getIngressBw() >= zaBw
                    && zMatching.get(0).getIngressBw() >= azBw && zMatching.get(0).getEgressBw() >= zaBw;

        }
    }

}
