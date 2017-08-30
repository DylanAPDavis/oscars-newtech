package net.es.oscars.dto.resv.precheck;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreCheckResponse {
    public enum PrecheckResult {
        SUCCESS,
        UNSUCCESSFUL,
    }
    private String connectionId;
    private PrecheckResult precheckResult;
    private Set<String> nodesToHighlight;
    private Set<String> linksToHighlight;
}
