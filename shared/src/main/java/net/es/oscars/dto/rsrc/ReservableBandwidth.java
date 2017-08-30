package net.es.oscars.dto.rsrc;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservableBandwidth {
    private String topoVertexUrn;

    private Integer bandwidth;

    private Integer ingressBw;

    private Integer egressBw;
}
