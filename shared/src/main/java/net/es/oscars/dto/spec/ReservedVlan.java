package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.topo.Urn;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservedVlan {

    @NonNull
    private String urn;

    private Integer vlan;

    private Instant beginning;

    private Instant ending;


}
