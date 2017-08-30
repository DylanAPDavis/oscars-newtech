.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars CoreUnitTestConfiguration

.. java:import:: net.es.oscars.pce PCEException

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.topo.dao UrnAdjcyRepository

.. java:import:: net.es.oscars.topo.dao UrnRepository

.. java:import:: org.junit Test

.. java:import:: org.junit.runner RunWith

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.boot.test SpringApplicationConfiguration

.. java:import:: org.springframework.test.context.junit4 SpringJUnit4ClassRunner

.. java:import:: org.springframework.transaction.annotation Transactional

TopoPopTest
===========

.. java:package:: net.es.oscars.topo
   :noindex:

.. java:type:: @Slf4j @RunWith @SpringApplicationConfiguration @Transactional public class TopoPopTest

Methods
-------
testSave
^^^^^^^^

.. java:method:: @Test public void testSave() throws PCEException, PSSException
   :outertype: TopoPopTest

