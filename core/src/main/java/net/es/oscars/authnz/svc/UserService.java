package net.es.oscars.authnz.svc;

import net.es.oscars.authnz.dao.UserRepository;
import net.es.oscars.authnz.ent.EUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {

    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;

    }

    public EUser save(EUser user) {
        return userRepo.save(user);
    }

}
