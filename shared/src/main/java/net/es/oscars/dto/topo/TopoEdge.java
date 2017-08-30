package net.es.oscars.dto.topo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.dto.topo.enums.Layer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopoEdge {
    private TopoVertex a;

    private TopoVertex z;

    private Long metric;

    private Layer layer;


}
