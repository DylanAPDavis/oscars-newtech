package net.es.oscars.webui;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service(value = "restAuthProvider")
public class RestAuthProvider implements AuthenticationProvider {
    private RestTemplate restTemplate;

    @Autowired
    public RestAuthProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String submittedPwd = authentication.getCredentials().toString();
        String encoded = passwordEncoder().encode(submittedPwd);
        log.info("username: " + username);
        log.info("encoded: " + encoded);

        String restPath = "https://localhost:8000/users/";
        User[] users = restTemplate.getForObject(restPath, User[].class);

        User userDto;

        // no users exist; allow login with any username / password
        if (users.length == 0) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("USER"));
            grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
            return new UsernamePasswordAuthenticationToken(username, submittedPwd, grantedAuths);


        } else {

            restPath = "https://localhost:8000/users/get/";
            try {
                userDto = restTemplate.getForObject(restPath + username, User.class);

            } catch (HttpClientErrorException ex) {
                throw new BadCredentialsException("Bad credentials " + ex.getMessage());
            }
        }

        String storedPassword = userDto.getPassword();
        // log.info("stored: "+ storedPassword);
        if (passwordEncoder().matches(submittedPwd, storedPassword)) {
            log.info("matched passwords for " + username);
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("USER"));


            if (userDto.getPermissions().isAdminAllowed()) {
                grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
            } else if (users.length == 1) {
                // only one user defined : make them an admin
                log.info("only one user defined in DB; setting them as admin");
                grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
            }

            return new UsernamePasswordAuthenticationToken(username, submittedPwd, grantedAuths);
        } else {
            throw new BadCredentialsException("Unable to authenticate");
        }

    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
