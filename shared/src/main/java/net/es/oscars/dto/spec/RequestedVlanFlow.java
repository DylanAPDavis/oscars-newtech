package net.es.oscars.dto.spec;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestedVlanFlow {

    private Long id;

    private Set<RequestedVlanJunction> junctions;

    private Set<RequestedVlanPipe> pipes;

    @NonNull
    private Integer minPipes;

    @NonNull
    private Integer maxPipes;

    @NonNull
    private String containerConnectionId;       // Unique ID of the containing Connection
}
