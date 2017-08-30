.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.topo.enums Layer

.. java:import:: net.es.oscars.pce PCEException

.. java:import:: net.es.oscars.pce TopPCE

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.dto.pss EthJunctionType

.. java:import:: net.es.oscars.resv.ent RequestedVlanFlowE

.. java:import:: net.es.oscars.resv.ent RequestedVlanJunctionE

.. java:import:: net.es.oscars.resv.ent SpecificationE

.. java:import:: net.es.oscars.resv.dao SpecificationRepository

.. java:import:: net.es.oscars.topo.dao UrnRepository

.. java:import:: net.es.oscars.topo.ent UrnE

.. java:import:: net.es.oscars.dto.topo.enums DeviceType

.. java:import:: net.es.oscars.dto.topo.enums DeviceModel

.. java:import:: net.es.oscars.dto.topo.enums UrnType

.. java:import:: org.junit Test

.. java:import:: org.junit.runner RunWith

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.boot.test SpringApplicationConfiguration

.. java:import:: org.springframework.test.context.junit4 SpringJUnit4ClassRunner

.. java:import:: org.springframework.transaction.annotation Transactional

.. java:import:: java.util ArrayList

.. java:import:: java.util HashSet

CoreTest
========

.. java:package:: net.es.oscars
   :noindex:

.. java:type:: @Slf4j @RunWith @SpringApplicationConfiguration @Transactional public class CoreTest

Methods
-------
testNoFixtures
^^^^^^^^^^^^^^

.. java:method:: @Test public void testNoFixtures() throws PCEException
   :outertype: CoreTest

testSpecification
^^^^^^^^^^^^^^^^^

.. java:method:: public void testSpecification() throws PCEException, PSSException
   :outertype: CoreTest

