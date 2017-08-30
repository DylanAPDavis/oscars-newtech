.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.auth User

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.context.annotation Bean

.. java:import:: org.springframework.security.authentication AuthenticationProvider

.. java:import:: org.springframework.security.authentication BadCredentialsException

.. java:import:: org.springframework.security.authentication UsernamePasswordAuthenticationToken

.. java:import:: org.springframework.security.core Authentication

.. java:import:: org.springframework.security.core AuthenticationException

.. java:import:: org.springframework.security.core GrantedAuthority

.. java:import:: org.springframework.security.core.authority SimpleGrantedAuthority

.. java:import:: org.springframework.security.crypto.bcrypt BCryptPasswordEncoder

.. java:import:: org.springframework.security.crypto.password PasswordEncoder

.. java:import:: org.springframework.stereotype Service

.. java:import:: org.springframework.web.client HttpClientErrorException

.. java:import:: org.springframework.web.client RestTemplate

.. java:import:: java.util ArrayList

.. java:import:: java.util List

RestAuthProvider
================

.. java:package:: net.es.oscars.webui
   :noindex:

.. java:type:: @Slf4j @Service public class RestAuthProvider implements AuthenticationProvider

Methods
-------
authenticate
^^^^^^^^^^^^

.. java:method:: @Override public Authentication authenticate(Authentication authentication) throws AuthenticationException
   :outertype: RestAuthProvider

passwordEncoder
^^^^^^^^^^^^^^^

.. java:method:: @Bean public PasswordEncoder passwordEncoder()
   :outertype: RestAuthProvider

supports
^^^^^^^^

.. java:method:: @Override public boolean supports(Class<?> authentication)
   :outertype: RestAuthProvider

