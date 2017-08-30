package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.topo.BidirectionalPath;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservedVlanFlow {
    private Long id;

    private Set<ReservedVlanJunction> junctions;

    private Set<ReservedEthPipe> ethPipes;

    private Set<ReservedMplsPipe> mplsPipes;

    private Set<BidirectionalPath> allPaths;

    @NonNull
    private String containerConnectionId;       // Unique ID of the containing Connection
}
