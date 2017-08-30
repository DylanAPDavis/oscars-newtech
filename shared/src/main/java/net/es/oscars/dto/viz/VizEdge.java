package net.es.oscars.dto.viz;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VizEdge {
    String id;
    String to;
    String from;
    Integer value;
    String color;
    String arrows;
    Boolean arrowStrikethrough;
    String title;
    String label;
}
