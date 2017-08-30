package net.es.oscars.dto.spec;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VlanFlow {

    @NonNull
    private String aDeviceUrn;

    @NonNull
    private String zDeviceUrn;

    @NonNull
    private String aUrn;

    @NonNull
    private String zUrn;

    @NonNull
    private String aVlanExpression;

    @NonNull
    private String zVlanExpression;

    @NonNull
    private Integer azMbps;

    @NonNull
    private Integer zaMbps;

    @NonNull
    private List<String> azERO;

    @NonNull
    private List<String> zaERO;

    @NonNull
    private Set<String> urnBlacklist;

    @NonNull
    private PalindromicType palindromic;

    @NonNull
    private SurvivabilityType survivability;

    @NonNull
    private Integer numDisjointPaths;

}
