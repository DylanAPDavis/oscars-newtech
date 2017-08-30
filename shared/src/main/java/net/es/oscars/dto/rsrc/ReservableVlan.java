package net.es.oscars.dto.rsrc;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.dto.IntRange;
import net.es.oscars.dto.resv.ResourceType;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservableVlan {
    private String topoVertexUrn;
    private Set<IntRange> vlanRanges;


}
