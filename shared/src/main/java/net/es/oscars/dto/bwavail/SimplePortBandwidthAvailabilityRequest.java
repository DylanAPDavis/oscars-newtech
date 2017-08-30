package net.es.oscars.dto.bwavail;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplePortBandwidthAvailabilityRequest {
    @NonNull
    private String startDate;

    @NonNull
    private String endDate;
}
