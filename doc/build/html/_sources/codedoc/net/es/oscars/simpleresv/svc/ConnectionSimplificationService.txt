.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.spec ReservedVlanFlow

.. java:import:: net.es.oscars.dto.topo BidirectionalPath

.. java:import:: net.es.oscars.st.oper OperState

.. java:import:: net.es.oscars.st.prov ProvState

.. java:import:: net.es.oscars.st.resv ResvState

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Service

.. java:import:: java.util HashSet

.. java:import:: java.util Set

ConnectionSimplificationService
===============================

.. java:package:: net.es.oscars.simpleresv.svc
   :noindex:

.. java:type:: @Service @Slf4j public class ConnectionSimplificationService

Methods
-------
parseStates
^^^^^^^^^^^

.. java:method:: public Status parseStates(States states)
   :outertype: ConnectionSimplificationService

simplifyConnection
^^^^^^^^^^^^^^^^^^

.. java:method:: public ReservationDetails simplifyConnection(Connection conn)
   :outertype: ConnectionSimplificationService

