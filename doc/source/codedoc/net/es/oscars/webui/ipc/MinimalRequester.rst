.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv Connection

.. java:import:: net.es.oscars.webui.dto MinimalConnectionBuilder

.. java:import:: net.es.oscars.webui.dto MinimalRequest

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: org.springframework.web.client RestTemplate

MinimalRequester
================

.. java:package:: net.es.oscars.webui.ipc
   :noindex:

.. java:type:: @Slf4j @Component public class MinimalRequester

Methods
-------
holdMinimal
^^^^^^^^^^^

.. java:method:: public Connection holdMinimal(MinimalRequest minimalRequest)
   :outertype: MinimalRequester

