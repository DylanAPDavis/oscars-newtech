package net.es.oscars.dto.pss.params.mx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.dto.pss.params.Lsp;
import net.es.oscars.dto.pss.params.MplsPath;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MxParams {


    private Map<String, Lsp> lsps;

    private List<MplsPath> paths;


    private Map<String, MxQos> policing;

    private MxVpls mxVpls;

    private String loopbackInterface;
    private String loopbackAddress;
    private Boolean applyQos;


}
