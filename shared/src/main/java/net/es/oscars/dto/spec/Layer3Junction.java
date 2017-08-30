package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.pss.Layer3JunctionType;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Layer3Junction {
    private Long id;

    @NonNull
    private String deviceUrn;

    @NonNull
    private Layer3JunctionType junctionType;

    private Set<Layer3Fixture> fixtures;

    private Set<String> resourceIds;

}
