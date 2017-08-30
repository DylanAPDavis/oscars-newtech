package net.es.oscars.dto.resv;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicCircuitSpecification {
    String connectionId;

    String description;

    String start;

    String end;

    String sourcePort;

    String sourceDevice;

    String destPort;

    String destDevice;

    Integer azMbps;

    Integer zaMbps;
}
