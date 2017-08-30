package net.es.oscars.dto.topo;

import lombok.*;
import net.es.oscars.dto.topo.enums.Layer;
import net.es.oscars.dto.topo.enums.UrnType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DevicePortMap {
    Map<String, Set<String>> map;
}
