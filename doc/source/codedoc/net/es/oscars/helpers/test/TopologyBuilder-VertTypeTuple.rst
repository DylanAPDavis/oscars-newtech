.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.topo TopoEdge

.. java:import:: net.es.oscars.dto.topo TopoVertex

.. java:import:: net.es.oscars.dto.topo.enums Layer

.. java:import:: net.es.oscars.dto.topo.enums VertexType

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

TopologyBuilder.VertTypeTuple
=============================

.. java:package:: net.es.oscars.helpers.test
   :noindex:

.. java:type::  class VertTypeTuple<V, T>
   :outertype: TopologyBuilder

Fields
------
t
^

.. java:field:: public final T t
   :outertype: TopologyBuilder.VertTypeTuple

v
^

.. java:field:: public final V v
   :outertype: TopologyBuilder.VertTypeTuple

Constructors
------------
VertTypeTuple
^^^^^^^^^^^^^

.. java:constructor:: public VertTypeTuple(V vSpec, T tSpec)
   :outertype: TopologyBuilder.VertTypeTuple

