package net.es.oscars.dto.topo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.dto.topo.enums.PortLayer;
import net.es.oscars.dto.topo.enums.VertexType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopoVertex {
    private String urn;
    private VertexType vertexType;
    private PortLayer portLayer;

    public TopoVertex(String theURN, VertexType theVertexType)
    {
        urn = theURN;
        vertexType = theVertexType;
        portLayer = PortLayer.NONE;
    }


}
