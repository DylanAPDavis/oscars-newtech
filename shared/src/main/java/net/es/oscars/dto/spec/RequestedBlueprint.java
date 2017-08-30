package net.es.oscars.dto.spec;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestedBlueprint {

    private Long id;

    @NonNull
    private RequestedVlanFlow vlanFlow;

    @NonNull
    private Layer3Flow layer3Flow;

    @NonNull
    private String containerConnectionId;       // Unique ID of the containing Connection
}