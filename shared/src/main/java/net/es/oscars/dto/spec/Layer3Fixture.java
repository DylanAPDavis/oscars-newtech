package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.pss.Layer3FixtureType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Layer3Fixture {

    private Long id;

    @NonNull
    private String portUrn;

    private Integer vlanId;

    private String vlanExpression;

    @NonNull
    private Layer3FixtureType fixtureType;

    @NonNull
    private Integer inMbps;

    @NonNull
    private Integer egMbps;

}
