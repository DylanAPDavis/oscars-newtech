.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.pss.dao TemplateRepository

.. java:import:: net.es.oscars.pss.ent TemplateE

.. java:import:: net.es.oscars.pss.prop PssConfig

.. java:import:: net.es.oscars.pss.tpl Stringifier

.. java:import:: org.apache.commons.io FileUtils

.. java:import:: org.apache.commons.io FilenameUtils

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: javax.annotation PostConstruct

.. java:import:: java.io File

.. java:import:: java.io IOException

.. java:import:: java.util ArrayList

.. java:import:: java.util List

TemplateImporter
================

.. java:package:: net.es.oscars.pss.pop
   :noindex:

.. java:type:: @Slf4j @Component public class TemplateImporter

Methods
-------
attemptImport
^^^^^^^^^^^^^

.. java:method:: @PostConstruct public void attemptImport()
   :outertype: TemplateImporter

