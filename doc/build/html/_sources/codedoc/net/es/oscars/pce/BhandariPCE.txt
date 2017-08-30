.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.topo TopoEdge

.. java:import:: net.es.oscars.dto.topo TopoVertex

.. java:import:: net.es.oscars.dto.topo Topology

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: java.util.stream Collectors

BhandariPCE
===========

.. java:package:: net.es.oscars.pce
   :noindex:

.. java:type:: @Slf4j @Component public class BhandariPCE

Methods
-------
computeDisjointPaths
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public List<List<TopoEdge>> computeDisjointPaths(Topology topo, TopoVertex source, TopoVertex dest, Integer k)
   :outertype: BhandariPCE

