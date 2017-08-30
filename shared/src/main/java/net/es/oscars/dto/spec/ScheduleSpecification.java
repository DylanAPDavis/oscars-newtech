package net.es.oscars.dto.spec;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSpecification {

    @NonNull
    private List<Date> startDates;

    @NonNull
    private List<Date> endDates;

    private Long minimumDuration;

}
