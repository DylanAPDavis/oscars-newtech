package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.pss.EthJunctionType;
import net.es.oscars.dto.topo.Urn;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservedVlanJunction {

    private Long id;

    @NonNull
    private String deviceUrn;

    @NonNull
    private EthJunctionType junctionType;

    private Set<ReservedVlanFixture> fixtures;

    private Set<ReservedPssResource> reservedPssResources;

    private Set<ReservedVlan> reservedVlans;

}
