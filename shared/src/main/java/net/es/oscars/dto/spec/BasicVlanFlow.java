package net.es.oscars.dto.spec;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicVlanFlow {

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
    private PalindromicType palindromic;

    @NonNull
    private SurvivabilityType survivability;



}
