package net.es.oscars.topo.beans;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortBwVlan {
    @NonNull
    private Set<IntRange> vlanRanges;
    @NonNull
    private String vlanExpression;
    @NonNull
    private Integer ingressBandwidth;
    @NonNull
    private Integer egressBandwidth;

}
