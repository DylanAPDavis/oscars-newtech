package net.es.oscars.dto.bwavail;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBandwidthAvailabilityRequest {

    @NonNull
    private String startDate;
    @NonNull
    private String endDate;

    @NonNull
    private Integer minAzBandwidth;
    @NonNull
    private Integer minZaBandwidth;

    private List<List<String>> azEros;
    private List<List<String>> zaEros;

    private String srcDevice;
    private Set<String> srcPorts;
    private String dstDevice;
    private Set<String> dstPorts;

    private Boolean disjointPaths;
    private Integer numPaths;
}
