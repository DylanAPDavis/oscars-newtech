package net.es.oscars.dto.bwavail;


import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortBandwidthAvailabilityResponse
{
    @NonNull
    private Map<String, List<Integer>> bwAvailabilityMap;   //URN-> [inBW, egBW]
}
