package net.es.oscars.dto.topo;

import lombok.*;
import net.es.oscars.dto.topo.enums.Layer;
import net.es.oscars.dto.topo.enums.UrnType;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Urn {

    public String toString() {
        return this.getUrn();
    }

    private Long id;

    @NonNull
    private String urn;


    // TODO: validity periods? but this will do for now
    @NonNull
    private Boolean valid;

    @NonNull
    private UrnType urnType;

    private Set<Layer> capabilities = new HashSet<>();


}
