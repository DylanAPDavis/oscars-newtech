.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.authnz.dao UserRepository

.. java:import:: net.es.oscars.authnz.ent EPermissions

.. java:import:: net.es.oscars.authnz.ent EUser

.. java:import:: net.es.oscars.authnz.prop AuthnzProperties

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.security.crypto.bcrypt BCryptPasswordEncoder

.. java:import:: org.springframework.stereotype Component

.. java:import:: javax.annotation PostConstruct

.. java:import:: java.util List

AuthnzPopulator
===============

.. java:package:: net.es.oscars.authnz.pop
   :noindex:

.. java:type:: @Slf4j @Component public class AuthnzPopulator

Constructors
------------
AuthnzPopulator
^^^^^^^^^^^^^^^

.. java:constructor:: @Autowired public AuthnzPopulator(UserRepository userRepo, AuthnzProperties properties)
   :outertype: AuthnzPopulator

Methods
-------
initializeUserDb
^^^^^^^^^^^^^^^^

.. java:method:: @PostConstruct public void initializeUserDb()
   :outertype: AuthnzPopulator

