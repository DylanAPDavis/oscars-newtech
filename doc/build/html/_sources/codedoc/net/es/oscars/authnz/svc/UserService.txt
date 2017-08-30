.. java:import:: net.es.oscars.authnz.dao UserRepository

.. java:import:: net.es.oscars.authnz.ent EUser

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Service

.. java:import:: org.springframework.transaction.annotation Transactional

UserService
===========

.. java:package:: net.es.oscars.authnz.svc
   :noindex:

.. java:type:: @Service @Transactional public class UserService

Constructors
------------
UserService
^^^^^^^^^^^

.. java:constructor:: @Autowired public UserService(UserRepository userRepo)
   :outertype: UserService

Methods
-------
save
^^^^

.. java:method:: public EUser save(EUser user)
   :outertype: UserService

