package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.resv.ResourceType;
import net.es.oscars.dto.topo.Urn;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservedPssResource {

    private Long id;

    @NonNull
    private String urn;

    @NonNull
    private ResourceType resourceType;

    private Integer resource;

    private Instant beginning;

    private Instant ending;


}
