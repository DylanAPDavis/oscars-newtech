package net.es.oscars.authnz.dao;

import net.es.oscars.authnz.ent.EUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<EUser, Long> {

    List<EUser> findAll();
    Optional<EUser> findByUsername(String username);
    Optional<EUser> findByCertSubject(String certSubject);

}