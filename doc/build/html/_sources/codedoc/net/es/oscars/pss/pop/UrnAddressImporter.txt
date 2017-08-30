.. java:import:: com.fasterxml.jackson.databind ObjectMapper

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.helpers JsonHelper

.. java:import:: net.es.oscars.pss.dao TemplateRepository

.. java:import:: net.es.oscars.pss.dao UrnAddressRepository

.. java:import:: net.es.oscars.pss.ent TemplateE

.. java:import:: net.es.oscars.pss.ent UrnAddressE

.. java:import:: net.es.oscars.pss.prop PssConfig

.. java:import:: net.es.oscars.pss.tpl Stringifier

.. java:import:: net.es.oscars.topo.pop Device

.. java:import:: org.apache.commons.io FileUtils

.. java:import:: org.apache.commons.io FilenameUtils

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: javax.annotation PostConstruct

.. java:import:: java.io File

.. java:import:: java.io IOException

.. java:import:: java.util ArrayList

.. java:import:: java.util Arrays

.. java:import:: java.util List

UrnAddressImporter
==================

.. java:package:: net.es.oscars.pss.pop
   :noindex:

.. java:type:: @Slf4j @Component public class UrnAddressImporter

Methods
-------
attemptImport
^^^^^^^^^^^^^

.. java:method:: @PostConstruct public void attemptImport()
   :outertype: UrnAddressImporter

