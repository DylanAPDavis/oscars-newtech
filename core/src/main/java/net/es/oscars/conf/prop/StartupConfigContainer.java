package net.es.oscars.conf.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "startup")
public class StartupConfigContainer {
    public StartupConfigContainer() {

    }
    @NestedConfigurationProperty
    StartupConfigEntry defaults;

    List<StartupConfigEntry> modules;


}
