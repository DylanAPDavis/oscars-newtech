package net.es.oscars.dto.bwavail;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BandwidthAvailabilityRequest {

    @NonNull
    private Date startDate;
    @NonNull
    private Date endDate;

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
