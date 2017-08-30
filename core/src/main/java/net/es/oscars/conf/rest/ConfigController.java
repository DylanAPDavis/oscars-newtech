package net.es.oscars.conf.rest;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.conf.dao.ConfigRepository;
import net.es.oscars.conf.ent.EStartupConfig;
import net.es.oscars.conf.pop.ConfigPopulator;
import net.es.oscars.dto.cfg.StartupConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ConfigController {
    private ConfigRepository repository;
    private ConfigPopulator populator;

    @Autowired
    public ConfigController(ConfigRepository repository, ConfigPopulator populator) {
        this.populator = populator;
        this.repository = repository;
    }


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException(NoSuchElementException ex) {
        // LOG.warn("user requested a strResource which didn't exist", ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // LOG.warn("user requested a strResource which didn't exist", ex);
    }

    @RequestMapping(value = "/configs/all", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public List<String> listComponents() {
        log.info("listing all");

        return repository.findAll().stream().map(
                EStartupConfig::getName).collect(Collectors.toCollection(ArrayList::new));
    }


    @RequestMapping(value = "/configs/update", method = RequestMethod.POST)
    @ResponseBody
    public StartupConfig update(@RequestBody StartupConfig startupConfig) {
        log.info("updating " + startupConfig.getName());

        EStartupConfig configEnt = repository.findByName(startupConfig.getName()).orElseThrow(NoSuchElementException::new);


        configEnt.setConfigJson(startupConfig.getConfigJson());
        repository.save(configEnt);
        return startupConfig;
    }



    @RequestMapping(value = "/configs/ready", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public void ready() {
        log.info("checking if ready...");
        int maxTimeout = 30000;
        int waitedFor = 0;

        while (!this.populator.isStarted() && waitedFor < maxTimeout) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                log.error("interrupted", ex);
            }
            waitedFor += 5000;
        }

        if (!this.populator.isStarted()) {
            throw new NoSuchElementException("could not populate configs");
        }
    }


    @RequestMapping(value = "/configs/get/{component}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public String getConfig(@PathVariable("component") String component) {
        log.info("retrieving " + component);

        Optional<EStartupConfig> maybeConfig = repository.findByName(component);
        if (maybeConfig.isPresent()) {
            EStartupConfig configEnt = maybeConfig.get();
            return configEnt.getConfigJson();
        } else {
            throw new NoSuchElementException();
        }
    }

    @RequestMapping(value = "/configs/delete/{component}", method = RequestMethod.GET)
    @ResponseBody
    public String delConfig(@PathVariable("component") String component) {
        log.info("deleting " + component);

        Optional<EStartupConfig> maybeConfig = repository.findByName(component);
        maybeConfig.orElseThrow(NoSuchElementException::new);
        EStartupConfig configEnt = maybeConfig.get();

        repository.delete(configEnt);
        return "deleted";
    }

}