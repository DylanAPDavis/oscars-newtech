package net.es.oscars.dto.topo;



import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BidirectionalPath {

    private List<Edge> azPath;
    private List<Edge> zaPath;

    @NonNull
    private final String uniqueID = UUID.randomUUID().toString();
}
