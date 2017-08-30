.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars CoreUnitTestConfiguration

.. java:import:: net.es.oscars.dto.spec PalindromicType

.. java:import:: net.es.oscars.dto.spec SurvivabilityType

.. java:import:: net.es.oscars.helpers RequestedEntityBuilder

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.resv.dao ReservedBandwidthRepository

.. java:import:: net.es.oscars.resv.svc ResvService

.. java:import:: net.es.oscars.helpers.test TopologyBuilder

.. java:import:: net.es.oscars.dto.topo TopoEdge

.. java:import:: net.es.oscars.dto.topo TopoVertex

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

EroPceTest
==========

.. java:package:: net.es.oscars.pce
   :noindex:

.. java:type:: @Slf4j @RunWith @SpringApplicationConfiguration @Transactional public class EroPceTest

   Created by jeremy on 7/22/16. Tests End-to-End correctness of the PCE modules with specified EROs

Methods
-------
eroPceTestBadNonPalindrome1
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestBadNonPalindrome1()
   :outertype: EroPceTest

eroPceTestBadNonPalindrome2
^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestBadNonPalindrome2()
   :outertype: EroPceTest

eroPceTestDuplicateNode1
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestDuplicateNode1()
   :outertype: EroPceTest

eroPceTestDuplicateNode2
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestDuplicateNode2()
   :outertype: EroPceTest

eroPceTestNonPalindrome
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestNonPalindrome()
   :outertype: EroPceTest

eroPceTestNonPalindrome2
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestNonPalindrome2()
   :outertype: EroPceTest

eroPceTestPalindrome
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestPalindrome()
   :outertype: EroPceTest

eroPceTestSharedLink
^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroPceTestSharedLink()
   :outertype: EroPceTest

eroSpecTestEmptyAZ
^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroSpecTestEmptyAZ()
   :outertype: EroPceTest

eroSpecTestEmptyZA
^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroSpecTestEmptyZA()
   :outertype: EroPceTest

eroSpecTestSharedLinkInsufficientBW
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroSpecTestSharedLinkInsufficientBW()
   :outertype: EroPceTest

eroSpecTestSharedLinkSufficientBW
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void eroSpecTestSharedLinkSufficientBW()
   :outertype: EroPceTest

multiMplsPipeTestNonPal
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void multiMplsPipeTestNonPal()
   :outertype: EroPceTest

partialEroMultiIntermediateTest
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void partialEroMultiIntermediateTest()
   :outertype: EroPceTest

partialEroOneIntermediateTest
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void partialEroOneIntermediateTest()
   :outertype: EroPceTest

partialEroTwoIntermediateTest
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void partialEroTwoIntermediateTest()
   :outertype: EroPceTest

pceSubmitPartialEroMultiIntermediateTest
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @Test public void pceSubmitPartialEroMultiIntermediateTest()
   :outertype: EroPceTest

