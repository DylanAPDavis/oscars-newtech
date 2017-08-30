package net.es.oscars.dto.bwavail;

import lombok.*;

import java.util.Date;

/**
 * Created by jeremy on 11/25/16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortBandwidthAvailabilityRequest
{
    @NonNull
    private Date startDate;

    @NonNull
    private Date endDate;
}
