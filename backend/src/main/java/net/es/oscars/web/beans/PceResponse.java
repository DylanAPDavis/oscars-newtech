package net.es.oscars.web.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.resv.ent.EroHop;
import net.es.oscars.resv.enums.EroDirection;

import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties=true)
public class PceResponse {

    private PcePath shortest;
    private PcePath fits;
    private PcePath widest;
    private PcePath azBest;
    private PcePath zaBest;

}
