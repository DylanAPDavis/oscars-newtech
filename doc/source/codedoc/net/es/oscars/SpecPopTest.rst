.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.pss EthFixtureType

.. java:import:: net.es.oscars.dto.pss EthJunctionType

.. java:import:: net.es.oscars.dto.pss EthPipeType

.. java:import:: net.es.oscars.dto.spec PalindromicType

.. java:import:: net.es.oscars.dto.spec SurvivabilityType

.. java:import:: net.es.oscars.pce PCEException

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.resv.dao SpecificationRepository

.. java:import:: net.es.oscars.topo.dao UrnRepository

.. java:import:: net.es.oscars.topo.pop TopoImporter

.. java:import:: org.junit Before

.. java:import:: org.junit Test

.. java:import:: org.junit.runner RunWith

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.boot.test SpringApplicationConfiguration

.. java:import:: org.springframework.test.context.junit4 SpringJUnit4ClassRunner

.. java:import:: org.springframework.transaction.annotation Transactional

.. java:import:: java.io IOException

.. java:import:: java.time Instant

.. java:import:: java.time.temporal ChronoUnit

.. java:import:: java.util ArrayList

.. java:import:: java.util Collections

.. java:import:: java.util Date

.. java:import:: java.util HashSet

SpecPopTest
===========

.. java:package:: net.es.oscars
   :noindex:

.. java:type:: @Slf4j @RunWith @SpringApplicationConfiguration @Transactional public class SpecPopTest

Methods
-------
addEndpoints
^^^^^^^^^^^^

.. java:method:: public SpecificationE addEndpoints(SpecificationE spec)
   :outertype: SpecPopTest

getBasicSpec
^^^^^^^^^^^^

.. java:method:: public SpecificationE getBasicSpec()
   :outertype: SpecPopTest

startup
^^^^^^^

.. java:method:: @Before public void startup() throws IOException
   :outertype: SpecPopTest

testSave
^^^^^^^^

.. java:method:: @Test public void testSave() throws PCEException, PSSException
   :outertype: SpecPopTest

