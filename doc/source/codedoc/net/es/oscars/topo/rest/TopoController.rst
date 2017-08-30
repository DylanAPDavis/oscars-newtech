.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.rsrc ReservableBandwidth

.. java:import:: net.es.oscars.dto.spec ReservedBandwidth

.. java:import:: net.es.oscars.dto.topo Topology

.. java:import:: net.es.oscars.dto.topo.enums Layer

.. java:import:: net.es.oscars.resv.ent ReservedBandwidthE

.. java:import:: net.es.oscars.topo.ent ReservableBandwidthE

.. java:import:: net.es.oscars.topo.svc TopoService

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.dao DataIntegrityViolationException

.. java:import:: org.springframework.http HttpStatus

.. java:import:: org.springframework.stereotype Controller

TopoController
==============

.. java:package:: net.es.oscars.topo.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class TopoController

Constructors
------------
TopoController
^^^^^^^^^^^^^^

.. java:constructor:: @Autowired public TopoController(TopoService topoService)
   :outertype: TopoController

Methods
-------
devicePortMap
^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Map<String, Set<String>> devicePortMap()
   :outertype: TopoController

deviceVlanEdges
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<String> deviceVlanEdges(String device)
   :outertype: TopoController

devices
^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<String> devices()
   :outertype: TopoController

getAllReservedBandwidth
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<ReservedBandwidth> getAllReservedBandwidth()
   :outertype: TopoController

handleDataIntegrityViolationException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleDataIntegrityViolationException(DataIntegrityViolationException ex)
   :outertype: TopoController

handleResourceNotFoundException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleResourceNotFoundException(NoSuchElementException ex)
   :outertype: TopoController

portCapacity
^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<ReservableBandwidth> portCapacity()
   :outertype: TopoController

reservedBandwidth
^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<ReservedBandwidth> reservedBandwidth(List<String> resUrns)
   :outertype: TopoController

topo_layer
^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Topology topo_layer()
   :outertype: TopoController

topology
^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Topology topology()
   :outertype: TopoController

vlanEdges
^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<String> vlanEdges()
   :outertype: TopoController

