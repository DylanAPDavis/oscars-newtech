.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv Connection

.. java:import:: net.es.oscars.dto.resv ConnectionFilter

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.core ParameterizedTypeReference

.. java:import:: org.springframework.http HttpEntity

.. java:import:: org.springframework.http HttpMethod

.. java:import:: org.springframework.http ResponseEntity

.. java:import:: org.springframework.stereotype Component

.. java:import:: org.springframework.web.client RestTemplate

.. java:import:: java.util Set

ConnectionProvider
==================

.. java:package:: net.es.oscars.webui.ipc
   :noindex:

.. java:type:: @Slf4j @Component public class ConnectionProvider

Methods
-------
filtered
^^^^^^^^

.. java:method:: public Set<Connection> filtered(ConnectionFilter filter)
   :outertype: ConnectionProvider

