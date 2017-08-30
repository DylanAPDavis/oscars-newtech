.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.topo Topology

.. java:import:: net.es.oscars.dto.topo.enums Layer

.. java:import:: net.es.oscars.dto.viz Position

.. java:import:: net.es.oscars.topo.svc TopoService

.. java:import:: net.es.oscars.ui.pop UIPopulator

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.dao DataIntegrityViolationException

.. java:import:: org.springframework.http HttpStatus

.. java:import:: org.springframework.stereotype Controller

.. java:import:: java.util List

.. java:import:: java.util Map

.. java:import:: java.util NoSuchElementException

.. java:import:: java.util Set

UIController
============

.. java:package:: net.es.oscars.ui.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class UIController

Methods
-------
ui_positions
^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Map<String, Position> ui_positions()
   :outertype: UIController

