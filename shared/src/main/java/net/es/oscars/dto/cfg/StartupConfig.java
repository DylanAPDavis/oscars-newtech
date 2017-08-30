package net.es.oscars.dto.cfg;

import lombok.Data;

@Data
public class StartupConfig {
    public StartupConfig() {

    }

    private String name;
    private String configJson;

}
