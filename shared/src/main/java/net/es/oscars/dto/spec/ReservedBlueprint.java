package net.es.oscars.dto.spec;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservedBlueprint {

    private Long id;

    @NonNull
    private ReservedVlanFlow vlanFlow;

    @NonNull
    private String containerConnectionId;       // Unique ID of the containing Connection
}