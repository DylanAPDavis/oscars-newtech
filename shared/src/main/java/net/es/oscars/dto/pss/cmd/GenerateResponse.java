package net.es.oscars.dto.pss.cmd;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateResponse {
    private String device;
    private String connectionId;
    private String generated;
    private CommandType commandType;


}
