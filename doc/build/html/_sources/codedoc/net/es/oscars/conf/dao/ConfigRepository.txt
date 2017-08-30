.. java:import:: net.es.oscars.conf.ent EStartupConfig

.. java:import:: org.springframework.data.repository CrudRepository

.. java:import:: org.springframework.stereotype Repository

.. java:import:: java.util List

.. java:import:: java.util Optional

ConfigRepository
================

.. java:package:: net.es.oscars.conf.dao
   :noindex:

.. java:type:: @Repository public interface ConfigRepository extends CrudRepository<EStartupConfig, Long>

Methods
-------
findAll
^^^^^^^

.. java:method::  List<EStartupConfig> findAll()
   :outertype: ConfigRepository

findByName
^^^^^^^^^^

.. java:method::  Optional<EStartupConfig> findByName(String name)
   :outertype: ConfigRepository

