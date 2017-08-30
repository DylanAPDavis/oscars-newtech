package net.es.oscars.dto.spec;

import lombok.*;
import net.es.oscars.dto.pss.Layer3PipeType;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Layer3Pipe {
    private Long id;

    private Layer3Junction aJunction;

    private Layer3Junction zJunction;

    @NonNull
    private Integer azMbps;

    @NonNull
    private List<String> azERO;

    @NonNull
    private Layer3PipeType pipeType;

    private Set<String> resourceIds;

}
