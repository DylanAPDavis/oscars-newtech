package net.es.oscars.dto.topo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.dto.topo.enums.Layer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topology {
    public Layer layer;
    private Set<TopoEdge> edges = new HashSet<>();
    private Set<TopoVertex> vertices = new HashSet<>();


    public Optional<TopoVertex> getVertexByUrn(String urn){
        return this.vertices.stream()
                .filter(v -> v.getUrn().equals(urn))
                .findFirst();
    }
}
