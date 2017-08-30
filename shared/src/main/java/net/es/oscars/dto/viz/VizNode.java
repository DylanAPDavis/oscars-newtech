package net.es.oscars.dto.viz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VizNode {
    String id;
    String group;
    String label;
    Integer value;
    String title;
    String type;
    Map<String, Boolean> fixed;
    Integer x;
    Integer y;

}
