package net.es.oscars.dto.spec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.es.oscars.dto.pss.EthPipeType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReservedEthPipe {
    private Long id;

    @JsonProperty("aJunction")
    private ReservedVlanJunction aJunction;

    @JsonProperty("zJunction")
    private ReservedVlanJunction zJunction;

    private Set<ReservedBandwidth> reservedBandwidths;

    private Set<ReservedVlan> reservedVlans;

    private Set<ReservedPssResource> reservedPssResources;

    @NonNull
    private List<String> azERO;

    @NonNull
    private List<String> zaERO;

    @NonNull
    private EthPipeType pipeType;

    @NonNull
    private final String uniqueID = UUID.randomUUID().toString();
}
