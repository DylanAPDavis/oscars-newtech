.. java:import:: freemarker.cache StringTemplateLoader

.. java:import:: freemarker.template Configuration

.. java:import:: freemarker.template DefaultObjectWrapper

.. java:import:: freemarker.template Template

.. java:import:: freemarker.template TemplateException

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.pss.dao TemplateRepository

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: java.io IOException

.. java:import:: java.io StringWriter

.. java:import:: java.io Writer

.. java:import:: java.util Arrays

.. java:import:: java.util List

.. java:import:: java.util Map

Stringifier
===========

.. java:package:: net.es.oscars.pss.tpl
   :noindex:

.. java:type:: @Slf4j @Component public class Stringifier

Methods
-------
initialize
^^^^^^^^^^

.. java:method:: public void initialize()
   :outertype: Stringifier

stringify
^^^^^^^^^

.. java:method:: public String stringify(Map<String, Object> root, String templateFilename) throws IOException, TemplateException
   :outertype: Stringifier

