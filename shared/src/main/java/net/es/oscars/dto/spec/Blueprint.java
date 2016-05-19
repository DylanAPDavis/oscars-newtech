package net.es.oscars.dto.spec;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blueprint {

    private Long id;

    @NonNull
    private Set<VlanFlow> vlanFlows;

    @NonNull
    private Set<Layer3Flow> layer3Flows;
}