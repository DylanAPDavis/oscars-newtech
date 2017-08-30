package net.es.oscars.dto.resv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservedResource {

    private ResourceType resourceType;

    private String strResource;

    private Integer intResource;

    private List<String> urns;

    private Instant beginning;

    private Instant ending;

}
