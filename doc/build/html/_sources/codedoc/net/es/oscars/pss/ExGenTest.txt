.. java:import:: freemarker.template TemplateException

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars CoreUnitTestConfiguration

.. java:import:: net.es.oscars.pss.cmd ExGenerationParams

.. java:import:: net.es.oscars.pss.cmd MxGenerationParams

.. java:import:: net.es.oscars.pss.dao TemplateRepository

.. java:import:: net.es.oscars.pss.tpl Assembler

.. java:import:: net.es.oscars.pss.tpl Stringifier

.. java:import:: org.junit Test

.. java:import:: org.junit.runner RunWith

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.boot.test SpringApplicationConfiguration

.. java:import:: org.springframework.test.context.junit4 SpringJUnit4ClassRunner

.. java:import:: java.io IOException

.. java:import:: java.util ArrayList

.. java:import:: java.util HashMap

.. java:import:: java.util List

.. java:import:: java.util Map

ExGenTest
=========

.. java:package:: net.es.oscars.pss
   :noindex:

.. java:type:: @Slf4j @RunWith @SpringApplicationConfiguration public class ExGenTest

Methods
-------
genSetup
^^^^^^^^

.. java:method:: @Test public void genSetup() throws IOException, TemplateException
   :outertype: ExGenTest

