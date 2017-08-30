.. java:import:: com.fasterxml.jackson.core JsonProcessingException

.. java:import:: com.fasterxml.jackson.databind ObjectMapper

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.conf.dao ConfigRepository

.. java:import:: net.es.oscars.conf.ent EStartupConfig

.. java:import:: net.es.oscars.conf.prop StartupConfigContainer

.. java:import:: net.es.oscars.conf.prop StartupConfigEntry

.. java:import:: net.es.oscars.helpers JsonHelper

.. java:import:: org.apache.commons.lang3 SerializationUtils

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: javax.annotation PostConstruct

.. java:import:: java.util Optional

ConfigPopulator
===============

.. java:package:: net.es.oscars.conf.pop
   :noindex:

.. java:type:: @Slf4j @Component public class ConfigPopulator

Constructors
------------
ConfigPopulator
^^^^^^^^^^^^^^^

.. java:constructor:: @Autowired public ConfigPopulator(ConfigRepository repository, StartupConfigContainer startup)
   :outertype: ConfigPopulator

Methods
-------
initDefaults
^^^^^^^^^^^^

.. java:method:: @PostConstruct public void initDefaults() throws JsonProcessingException
   :outertype: ConfigPopulator

