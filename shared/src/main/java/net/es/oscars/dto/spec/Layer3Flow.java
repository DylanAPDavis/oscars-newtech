package net.es.oscars.dto.spec;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Layer3Flow {

    private Long id;

    private Set<Layer3Junction> junctions;

    private Set<Layer3Pipe> pipes;


}
