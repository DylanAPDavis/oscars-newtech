package net.es.oscars.dto.spec;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicVlanSpecification {

    private Long specificationId;

    @NonNull
    private String connectionId;

    @NonNull
    private String username;

    @NonNull
    private String description;

    @NonNull
    private ScheduleSpecification scheduleSpec;

    @NonNull
    private BasicVlanFlow basicVlanFlow;
}
