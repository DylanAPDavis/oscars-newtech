package net.es.oscars.dto.resv;

import lombok.*;
import net.es.oscars.dto.spec.ReservedBlueprint;
import net.es.oscars.dto.spec.Specification;
import net.es.oscars.st.oper.OperState;
import net.es.oscars.st.prov.ProvState;
import net.es.oscars.st.resv.ResvState;

import java.util.Date;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionFilter {

    private Integer numFilters;

    private Set<String> connectionIds;

    private Set<ResvState> resvStates;

    private Set<OperState> operStates;

    private Set<ProvState> provStates;

    private Set<String> userNames;

    private Set<Integer> minBandwidths;

    private Set<Integer> maxBandwidths;

    private Set<Date> startDates;

    private Set<Date> endDates;
}
