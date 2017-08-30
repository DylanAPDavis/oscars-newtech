.. java:import:: freemarker.template TemplateException

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.pss.cmd PSSOperation

.. java:import:: net.es.oscars.pss.cmd AluQos

.. java:import:: net.es.oscars.pss.tpl Stringifier

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: java.io IOException

.. java:import:: java.util HashMap

.. java:import:: java.util List

.. java:import:: java.util Map

AluQosGen
=========

.. java:package:: net.es.oscars.pss
   :noindex:

.. java:type:: @Component @Slf4j public class AluQosGen

Methods
-------
generate
^^^^^^^^

.. java:method:: public String generate(List<AluQos> qosList, boolean protect, boolean applyQos, PSSOperation op) throws IOException, TemplateException
   :outertype: AluQosGen

