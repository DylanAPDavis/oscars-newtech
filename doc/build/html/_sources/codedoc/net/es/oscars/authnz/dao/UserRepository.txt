.. java:import:: net.es.oscars.authnz.ent EUser

.. java:import:: org.springframework.data.repository CrudRepository

.. java:import:: org.springframework.stereotype Repository

.. java:import:: java.util List

.. java:import:: java.util Optional

UserRepository
==============

.. java:package:: net.es.oscars.authnz.dao
   :noindex:

.. java:type:: @Repository public interface UserRepository extends CrudRepository<EUser, Long>

Methods
-------
findAll
^^^^^^^

.. java:method::  List<EUser> findAll()
   :outertype: UserRepository

findByCertSubject
^^^^^^^^^^^^^^^^^

.. java:method::  Optional<EUser> findByCertSubject(String certSubject)
   :outertype: UserRepository

findByUsername
^^^^^^^^^^^^^^

.. java:method::  Optional<EUser> findByUsername(String username)
   :outertype: UserRepository

