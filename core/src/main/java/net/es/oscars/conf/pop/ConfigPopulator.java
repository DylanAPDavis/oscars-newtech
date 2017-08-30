package net.es.oscars.conf.pop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.es.oscars.conf.dao.ConfigRepository;
import net.es.oscars.conf.ent.EStartupConfig;
import net.es.oscars.conf.prop.StartupConfigContainer;
import net.es.oscars.conf.prop.StartupConfigEntry;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Component
public class ConfigPopulator {
    private boolean started = false;

    private StartupConfigContainer startup;
    private ConfigRepository repository;

    @Autowired
    public ConfigPopulator(ConfigRepository repository, StartupConfigContainer startup) {
        this.repository = repository;
        this.startup = startup;
    }
    public boolean isStarted() {
        return this.started;
    }

    @Transactional
    public void startup() throws JsonProcessingException {
        log.info("Initializing startup configs for OSCARS modules.");

        StartupConfigEntry defaultCfg = startup.getDefaults();
        log.info(defaultCfg.toString());
        if (startup.getModules() == null) {
            log.error("Could not retrieve OSCARS module configurations from application properties!");
            return;
        }

        for (StartupConfigEntry cfg : startup.getModules()) {

            String name = cfg.getName();
            Integer port = cfg.getServer_port();
            cfg = SerializationUtils.clone(defaultCfg);
            cfg.setName(name);
            cfg.setServer_port(port);


            Optional<EStartupConfig> maybeConfig = repository.findByName(name);

            if (maybeConfig.isPresent()) {
                log.debug("Startup config for OSCARS module " + name + " already in db.");

            } else {
                log.info("Startup config for OSCARS module " + name + " missing from DB; saving a new one from defaults.");
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(cfg);

                EStartupConfig configEnt = new EStartupConfig();
                configEnt.setName(name);
                configEnt.setConfigJson(jsonString);

                repository.save(configEnt);
            }

        }
        started = true;

    }
}
