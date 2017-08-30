package net.es.oscars.dto.bwavail;


import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BandwidthAvailabilityResponse {

    @NonNull
    private Map<String, Integer> minAvailableBwMap;

    @NonNull
    private Map<String, Map<Instant, Integer>> bwAvailabilityMap;

    @NonNull
    private Map<String, String> pathPairMap;

    @NonNull
    private Map<String, List<String>> pathNameMap;
}
