package net.es.oscars.dto.spec;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specification {

    private Long id;

    @NonNull
    private Integer version;

    @NonNull
    private String username;

    @NonNull
    private String description;

    @NonNull
    private String containerConnectionId;       // Unique ID of the containing Connection

    @NonNull
    private ScheduleSpecification scheduleSpec;

    @NonNull
    private RequestedBlueprint requested;


}
