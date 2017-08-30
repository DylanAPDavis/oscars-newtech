.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.pss EthFixtureType

.. java:import:: net.es.oscars.dto.pss EthJunctionType

.. java:import:: net.es.oscars.dto.pss EthPipeType

.. java:import:: net.es.oscars.st.oper OperState

.. java:import:: net.es.oscars.st.prov ProvState

.. java:import:: net.es.oscars.st.resv ResvState

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Service

.. java:import:: java.time.temporal ChronoUnit

ConnectionGenerationService
===========================

.. java:package:: net.es.oscars.simpleresv.svc
   :noindex:

.. java:type:: @Service @Slf4j public class ConnectionGenerationService

Methods
-------
buildConnection
^^^^^^^^^^^^^^^

.. java:method:: public Connection buildConnection(Specification spec)
   :outertype: ConnectionGenerationService

generateCircuitFlow
^^^^^^^^^^^^^^^^^^^

.. java:method:: public CircuitFlow generateCircuitFlow(String sourceDevice, Set<String> sourcePorts, String sourceVlan, String destDevice, Set<String> destPorts, String destVlan, Integer azMbps, Integer zaMbps, List<String> azRoute, List<String> zaRoute, Set<String> blacklist, String palindromic, String survivability, Integer numPaths)
   :outertype: ConnectionGenerationService

generateConnection
^^^^^^^^^^^^^^^^^^

.. java:method:: public Connection generateConnection(BasicCircuitSpecification bcs)
   :outertype: ConnectionGenerationService

generateConnection
^^^^^^^^^^^^^^^^^^

.. java:method:: public Connection generateConnection(CircuitSpecification cs)
   :outertype: ConnectionGenerationService

generateRequestedBlueprint
^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public RequestedBlueprint generateRequestedBlueprint(Set<CircuitFlow> flows, Integer maxNumFlows, Integer minNumFlows, String connectionId)
   :outertype: ConnectionGenerationService

generateRequestedFixtures
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Set<RequestedVlanFixture> generateRequestedFixtures(List<String> urns, List<String> vlans, List<Integer> inBws, List<Integer> egBws)
   :outertype: ConnectionGenerationService

generateRequestedJunction
^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public RequestedVlanJunction generateRequestedJunction(String name, Set<String> ports, String vlan, Integer inMbps, Integer egMbps)
   :outertype: ConnectionGenerationService

generateReservedVlanFlow
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public ReservedVlanFlow generateReservedVlanFlow(String connectionId)
   :outertype: ConnectionGenerationService

generateSchedule
^^^^^^^^^^^^^^^^

.. java:method:: public Schedule generateSchedule()
   :outertype: ConnectionGenerationService

generateScheduleSpecification
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public ScheduleSpecification generateScheduleSpecification(String start, String end)
   :outertype: ConnectionGenerationService

generateSpecification
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Specification generateSpecification(BasicCircuitSpecification bcs)
   :outertype: ConnectionGenerationService

generateSpecification
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Specification generateSpecification(CircuitSpecification cs)
   :outertype: ConnectionGenerationService

generateStates
^^^^^^^^^^^^^^

.. java:method:: public States generateStates()
   :outertype: ConnectionGenerationService

parsePalindrome
^^^^^^^^^^^^^^^

.. java:method:: public PalindromicType parsePalindrome(String pString)
   :outertype: ConnectionGenerationService

parseSurvivability
^^^^^^^^^^^^^^^^^^

.. java:method:: public SurvivabilityType parseSurvivability(String sString)
   :outertype: ConnectionGenerationService

