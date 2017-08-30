package net.es.oscars.dto.resv;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @NonNull
    private Date submitted;

    @NonNull
    private Date setup;

    @NonNull
    private Date teardown;


}
