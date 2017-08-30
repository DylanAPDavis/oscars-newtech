package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.pss.EthFixtureType;
import net.es.oscars.dto.topo.Urn;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservedVlanFixture {
    private Long id;

    @NonNull
    private String ifceUrn;

    private Set<ReservedVlan> reservedVlans;

    private ReservedBandwidth reservedBandwidth;

    private Set<ReservedPssResource> reservedPssResources;

    @NonNull
    private EthFixtureType fixtureType;

}
