package net.es.oscars.dto.resv;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CircuitFlow {

    Set<String> sourcePorts;

    @NonNull
    String sourceDevice;

    Set<String> destPorts;

    @NonNull
    String destDevice;

    Integer azMbps;

    Integer zaMbps;

    String sourceVlan;

    String destVlan;

    List<String> azRoute;

    List<String> zaRoute;

    Set<String> blacklist;

    String palindromic;

    String survivability;

    Integer numPaths;

    Integer priority;
}
