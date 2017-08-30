.. java:import:: com.fasterxml.jackson.core.type TypeReference

.. java:import:: com.fasterxml.jackson.databind ObjectMapper

.. java:import:: lombok Data

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.authnz.dao UserRepository

.. java:import:: net.es.oscars.authnz.ent EPermissions

.. java:import:: net.es.oscars.authnz.ent EUser

.. java:import:: net.es.oscars.authnz.prop AuthnzProperties

.. java:import:: net.es.oscars.dto.viz Position

.. java:import:: net.es.oscars.helpers JsonHelper

.. java:import:: net.es.oscars.ui.prop UIProperties

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.security.crypto.bcrypt BCryptPasswordEncoder

.. java:import:: org.springframework.stereotype Component

.. java:import:: javax.annotation PostConstruct

.. java:import:: java.io File

.. java:import:: java.io IOException

.. java:import:: java.util List

.. java:import:: java.util Map

UIPopulator
===========

.. java:package:: net.es.oscars.ui.pop
   :noindex:

.. java:type:: @Slf4j @Component @Data public class UIPopulator

Methods
-------
loadPositions
^^^^^^^^^^^^^

.. java:method:: @PostConstruct public void loadPositions() throws IOException
   :outertype: UIPopulator

