package net.es.oscars.dto.viz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VizGraph {
    List<VizNode> nodes;
    List<VizEdge> edges;
}
