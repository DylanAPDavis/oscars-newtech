package net.es.oscars.webui.ipc;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.resv.Connection;
import net.es.oscars.webui.dto.AdvancedRequest;
import net.es.oscars.webui.dto.ConnectionBuilder;
import net.es.oscars.webui.dto.MinimalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jeremy on 11/1/16.
 */
@Slf4j
@Component
public class PreChecker {

    @Autowired
    private PreChecker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private RestTemplate restTemplate;

    private final String oscarsUrl = "https://localhost:8000";


    public Connection preCheckMinimal(MinimalRequest minimalRequest) {
        log.info("Pre-checking minimal " + minimalRequest.getConnectionId());

        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
        Connection c = connectionBuilder.buildConnectionFromMinimalRequest(minimalRequest);
        return preCheck(c);
    }

    public Connection preCheckAdvanced(AdvancedRequest advancedRequest) {
        log.info("Pre-checking minimal " + advancedRequest.getConnectionId());

        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
        Connection c = connectionBuilder.buildConnectionFromAdvancedRequest(advancedRequest);
        return preCheck(c);
    }

    public Connection preCheck(Connection c) {
        String submitUrl = "/resv/connection/precheck";
        String restPath = oscarsUrl + submitUrl;

        Connection resultC = restTemplate.postForObject(restPath, c, Connection.class);

        if (resultC == null)
            log.info("Pre-Check result: UNSUCCESSFUL");
        else
            log.info("Pre-Check result: SUCCESS");

        //return c;
        return resultC;
    }
}
