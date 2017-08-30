package net.es.oscars.conf.dao;

import net.es.oscars.conf.ent.EStartupConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigRepository extends CrudRepository<EStartupConfig, Long> {

    List<EStartupConfig> findAll();
    Optional<EStartupConfig> findByName(String name);

}