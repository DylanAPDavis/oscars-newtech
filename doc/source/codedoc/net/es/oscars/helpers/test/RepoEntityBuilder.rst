.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.topo TopoEdge

.. java:import:: net.es.oscars.dto.topo TopoVertex

.. java:import:: net.es.oscars.dto.topo.enums VertexType

.. java:import:: net.es.oscars.topo.dao UrnAdjcyRepository

.. java:import:: net.es.oscars.topo.dao UrnRepository

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

RepoEntityBuilder
=================

.. java:package:: net.es.oscars.helpers.test
   :noindex:

.. java:type:: @Slf4j @Component public class RepoEntityBuilder

Methods
-------
addUrnToList
^^^^^^^^^^^^

.. java:method:: public UrnE addUrnToList(TopoVertex v, List<UrnE> urnList, Map<TopoVertex, TopoVertex> portToDeviceMap, List<Integer> floors, List<Integer> ceilings)
   :outertype: RepoEntityBuilder

addUrnToList
^^^^^^^^^^^^

.. java:method:: public UrnE addUrnToList(TopoVertex v, List<UrnE> urnList, Map<TopoVertex, TopoVertex> portToDeviceMap, Map<TopoVertex, List<Integer>> portBWs, List<Integer> floors, List<Integer> ceilings)
   :outertype: RepoEntityBuilder

buildIntRange
^^^^^^^^^^^^^

.. java:method:: public IntRangeE buildIntRange(Integer floor, Integer ceiling)
   :outertype: RepoEntityBuilder

buildIntRanges
^^^^^^^^^^^^^^

.. java:method:: public Set<IntRangeE> buildIntRanges(List<Integer> floors, List<Integer> ceilings)
   :outertype: RepoEntityBuilder

buildPortUrn
^^^^^^^^^^^^

.. java:method:: public UrnE buildPortUrn(TopoVertex vertex, VertexType parentType, Set<Layer> capabilities, Integer inBW, Integer egBW, List<Integer> floors, List<Integer> ceilings)
   :outertype: RepoEntityBuilder

buildReservableBandwidth
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public ReservableBandwidthE buildReservableBandwidth(Integer azMbps, Integer zaMbps)
   :outertype: RepoEntityBuilder

buildReservableVlan
^^^^^^^^^^^^^^^^^^^

.. java:method:: public ReservableVlanE buildReservableVlan(Set<IntRangeE> ranges)
   :outertype: RepoEntityBuilder

buildTopoEdge
^^^^^^^^^^^^^

.. java:method:: public TopoEdge buildTopoEdge(TopoVertex a, TopoVertex z, Layer layer, Long metric)
   :outertype: RepoEntityBuilder

buildTopoVertex
^^^^^^^^^^^^^^^

.. java:method:: public TopoVertex buildTopoVertex(String name, VertexType type)
   :outertype: RepoEntityBuilder

buildUrn
^^^^^^^^

.. java:method:: public UrnE buildUrn(TopoVertex vertex, VertexType vertexType, Set<Layer> capabilities, List<Integer> floors, List<Integer> ceilings)
   :outertype: RepoEntityBuilder

buildUrnAdjcy
^^^^^^^^^^^^^

.. java:method:: public UrnAdjcyE buildUrnAdjcy(TopoEdge edge, UrnE a, UrnE z)
   :outertype: RepoEntityBuilder

determineDeviceModel
^^^^^^^^^^^^^^^^^^^^

.. java:method:: public DeviceModel determineDeviceModel(VertexType type)
   :outertype: RepoEntityBuilder

determineDeviceType
^^^^^^^^^^^^^^^^^^^

.. java:method:: public DeviceType determineDeviceType(VertexType type)
   :outertype: RepoEntityBuilder

determineIfceType
^^^^^^^^^^^^^^^^^

.. java:method:: public IfceType determineIfceType(VertexType type)
   :outertype: RepoEntityBuilder

determineUrnType
^^^^^^^^^^^^^^^^

.. java:method:: public UrnType determineUrnType(VertexType type)
   :outertype: RepoEntityBuilder

getFromSet
^^^^^^^^^^

.. java:method:: public TopoVertex getFromSet(String name, Set<TopoVertex> vertices)
   :outertype: RepoEntityBuilder

getFromUrnList
^^^^^^^^^^^^^^

.. java:method:: public UrnE getFromUrnList(String name, List<UrnE> urns)
   :outertype: RepoEntityBuilder

populateRepos
^^^^^^^^^^^^^

.. java:method:: public void populateRepos(Collection<TopoVertex> vertices, Collection<TopoEdge> edges, Map<TopoVertex, TopoVertex> portToDeviceMap)
   :outertype: RepoEntityBuilder

populateRepos
^^^^^^^^^^^^^

.. java:method:: public void populateRepos(Collection<TopoVertex> vertices, Collection<TopoEdge> edges, Map<TopoVertex, TopoVertex> portToDeviceMap, Map<TopoVertex, List<Integer>> portBWs)
   :outertype: RepoEntityBuilder

populateRepos
^^^^^^^^^^^^^

.. java:method:: public void populateRepos(Collection<TopoVertex> vertices, Collection<TopoEdge> edges, Map<TopoVertex, TopoVertex> portToDeviceMap, Map<TopoVertex, List<Integer>> floorMap, Map<TopoVertex, List<Integer>> ceilingMap)
   :outertype: RepoEntityBuilder

populateRepos
^^^^^^^^^^^^^

.. java:method:: public void populateRepos(Collection<TopoVertex> vertices, Collection<TopoEdge> edges, Map<TopoVertex, TopoVertex> portToDeviceMap, Map<TopoVertex, List<Integer>> portBWs, Map<TopoVertex, List<Integer>> floorMap, Map<TopoVertex, List<Integer>> ceilingMap)
   :outertype: RepoEntityBuilder

