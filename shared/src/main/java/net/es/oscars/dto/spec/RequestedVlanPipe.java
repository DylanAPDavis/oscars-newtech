package net.es.oscars.dto.spec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.es.oscars.dto.pss.EthPipeType;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestedVlanPipe {

    private Long id;

    @JsonProperty("aJunction")
    @NonNull
    private RequestedVlanJunction aJunction;

    @JsonProperty("zJunction")
    @NonNull
    private RequestedVlanJunction zJunction;

    private Integer azMbps;

    private Integer zaMbps;

    private List<String> azERO;

    private List<String> zaERO;

    private Set<String> urnBlacklist;

    private EthPipeType pipeType;

    private PalindromicType eroPalindromic;

    private SurvivabilityType eroSurvivability;

    private Integer numPaths;

    private Integer priority;
}
