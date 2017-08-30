.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.rsrc ReservableBandwidth

.. java:import:: net.es.oscars.dto.topo Topology

.. java:import:: net.es.oscars.dto.viz Position

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: org.springframework.web.client RestTemplate

.. java:import:: java.util.stream Collectors

TopologyProvider
================

.. java:package:: net.es.oscars.webui.ipc
   :noindex:

.. java:type:: @Slf4j @Component public class TopologyProvider

Methods
-------
computeLinkCapacity
^^^^^^^^^^^^^^^^^^^

.. java:method:: public Integer computeLinkCapacity(String portA, String portZ, List<ReservableBandwidth> portCapacities)
   :outertype: TopologyProvider

devicePortMap
^^^^^^^^^^^^^

.. java:method:: public Map<String, Set<String>> devicePortMap()
   :outertype: TopologyProvider

getHubs
^^^^^^^

.. java:method:: public Map<String, Set<String>> getHubs()
   :outertype: TopologyProvider

getPortCapacities
^^^^^^^^^^^^^^^^^

.. java:method:: public List<ReservableBandwidth> getPortCapacities()
   :outertype: TopologyProvider

getPositions
^^^^^^^^^^^^

.. java:method:: public Map<String, Position> getPositions()
   :outertype: TopologyProvider

getTopology
^^^^^^^^^^^

.. java:method:: public Topology getTopology()
   :outertype: TopologyProvider

portDeviceMap
^^^^^^^^^^^^^

.. java:method:: public Map<String, String> portDeviceMap()
   :outertype: TopologyProvider

