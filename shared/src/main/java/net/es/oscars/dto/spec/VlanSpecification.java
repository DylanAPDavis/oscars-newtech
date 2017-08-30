package net.es.oscars.dto.spec;


import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VlanSpecification {

    @NonNull
    private String connectionId;

    @NonNull
    private String username;

    @NonNull
    private String description;

    @NonNull
    private ScheduleSpecification scheduleSpec;

    @NonNull
    private Set<VlanFlow> vlanFlows;

    @NonNull
    private Integer minNumFlows;

    @NonNull
    private Integer maxNumFlows;
}
