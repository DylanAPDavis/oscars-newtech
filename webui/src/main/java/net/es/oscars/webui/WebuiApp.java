package net.es.oscars.webui;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.rest.RestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = {WebuiApp.class, RestBean.class})
public class WebuiApp {

    public static void main(String[] args) {
        log.info("starting web ui");
        SpringApplication.run(WebuiApp.class, args);
    }

}