package net.es.oscars.dto.resv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CircuitSpecification {
    String connectionId;
    String username;
    String description;
    String start;
    String end;
    Set<CircuitFlow> flows;
    Integer minNumFlows;
    Integer maxNumFlows;
}
