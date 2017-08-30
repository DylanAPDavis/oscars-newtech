.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars CoreUnitTestConfiguration

.. java:import:: net.es.oscars.dto.spec PalindromicType

.. java:import:: net.es.oscars.dto.spec SurvivabilityType

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.helpers RequestedEntityBuilder

.. java:import:: net.es.oscars.resv.dao ReservedBandwidthRepository

.. java:import:: net.es.oscars.resv.svc ResvService

.. java:import:: net.es.oscars.helpers.test AsymmTopologyBuilder

.. java:import:: net.es.oscars.helpers.test TopologyBuilder

.. java:import:: net.es.oscars.topo.dao UrnRepository

.. java:import:: net.es.oscars.topo.svc TopoService

.. java:import:: org.junit Test

.. java:import:: org.junit.runner RunWith

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.boot.test SpringApplicationConfiguration

.. java:import:: org.springframework.test.context.junit4 SpringJUnit4ClassRunner

.. java:import:: org.springframework.transaction.annotation Transactional

.. java:import:: java.time Instant

.. java:import:: java.time.temporal ChronoUnit

.. java:import:: java.util.stream Collectors

.. java:import:: java.util.stream Stream

SequentialResvTest
==================

.. java:package:: net.es.oscars.pce
   :noindex:

.. java:type:: @Slf4j @RunWith @SpringApplicationConfiguration @Transactional public class SequentialResvTest

   Created by jeremy on 7/20/16.

   Tests End-to-End correctness of the PCE modules and reservation persistence through processing of sequential connections.

Methods
-------
sequentialResvAlternatePaths
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvAlternatePaths()
   :outertype: SequentialResvTest

sequentialResvIndependentSchedules1
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvIndependentSchedules1()
   :outertype: SequentialResvTest

sequentialResvIndependentSchedules2
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvIndependentSchedules2()
   :outertype: SequentialResvTest

sequentialResvInsufficientBW
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvInsufficientBW()
   :outertype: SequentialResvTest

sequentialResvInsufficientVlan
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvInsufficientVlan()
   :outertype: SequentialResvTest

sequentialResvInsufficientVlan2
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvInsufficientVlan2()
   :outertype: SequentialResvTest

sequentialResvNonPalindromicalPaths
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvNonPalindromicalPaths()
   :outertype: SequentialResvTest

sequentialResvSequentialPipes
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvSequentialPipes()
   :outertype: SequentialResvTest

sequentialResvTest1
^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void sequentialResvTest1()
   :outertype: SequentialResvTest

