package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.pss.EthFixtureType;
import net.es.oscars.dto.topo.Urn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestedVlanFixture {
    private Long id;


    @NonNull
    private String portUrn;

    private Integer vlanId;

    private String vlanExpression;

    @NonNull
    private EthFixtureType fixtureType;

    @NonNull
    private Integer inMbps;

    @NonNull
    private Integer egMbps;

}
