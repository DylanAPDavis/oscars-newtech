package net.es.oscars.dto.resv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.es.oscars.st.oper.OperState;
import net.es.oscars.st.prov.ProvState;
import net.es.oscars.st.resv.ResvState;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class States {

    private ResvState resv;

    private ProvState prov;

    private OperState oper;

}
