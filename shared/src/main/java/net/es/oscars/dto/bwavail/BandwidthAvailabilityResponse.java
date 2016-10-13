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
    private Long requestID;

    @NonNull
    private Date startDate;

    @NonNull
    private Date endDate;

    @NonNull
    private Integer minRequestedAzBandwidth;

    @NonNull
    private Integer minRequestedZaBandwidth;

    @NonNull
    private String srcDevice;

    @NonNull
    private String srcPort;

    @NonNull
    private String dstDevice;

    @NonNull
    private String dstPort;

    @NonNull
    private Integer minAvailableAzBandwidth;

    @NonNull
    private Integer maxAvailableAzBandwidth;

    @NonNull
    private Integer minAvailableZaBandwidth;

    @NonNull
    private Integer maxAvailableZaBandwidth;

    @NonNull
    private Map<Instant, Integer> azBwAvailMap;

    @NonNull
    private Map<Instant, Integer> zaBwAvailMap;
}