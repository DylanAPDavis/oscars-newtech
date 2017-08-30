package net.es.oscars.dto.resv;

import lombok.*;
import net.es.oscars.dto.spec.ReservedBlueprint;
import net.es.oscars.dto.spec.Specification;

import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connection {

    private Long id;

    @NonNull
    private String connectionId;

    @NonNull
    private States states;

    @NonNull
    private Schedule schedule;

    @NonNull
    private List<Date> reservedSchedule;

    @NonNull
    private Specification specification;

    @NonNull
    private ReservedBlueprint reserved;


}
