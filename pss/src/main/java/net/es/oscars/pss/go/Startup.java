package net.es.oscars.pss.go;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.pss.prop.StartupProps;
import net.es.oscars.pss.svc.CommandQueuer;
import net.es.oscars.pss.svc.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class Startup {

    private CommandQueuer queuer;
    private StartupProps props;
    private HealthService healthService;

    @Autowired
    public Startup(CommandQueuer queuer, HealthService healthService, StartupProps props) {
        this.queuer = queuer;
        this.props = props;
        this.healthService = healthService;
    }

    public void onStart() throws IOException {
        if (!props.getPerformControlPlaneCheck()) {
            log.info("config declines start up control plane check");
        } else {
            healthService.queueControlPlaneCheck(queuer, props.getControlPlaneCheckFilename());
        }
    }


}
