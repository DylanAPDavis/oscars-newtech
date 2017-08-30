package net.es.oscars.dto.resv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.dto.topo.BidirectionalPath;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetails {

    private String connectionId;

    private Status status;

    private String start;

    private String end;

    private Set<BidirectionalPath> paths;
}
