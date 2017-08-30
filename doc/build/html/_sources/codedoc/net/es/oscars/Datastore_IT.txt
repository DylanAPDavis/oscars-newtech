.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv ResourceType

.. java:import:: net.es.oscars.resv.dao ReservedPssResourceRepository

.. java:import:: net.es.oscars.resv.ent ReservedPssResourceE

.. java:import:: net.es.oscars.topo.ent UrnE

.. java:import:: net.es.oscars.topo.pop TopoImporter

.. java:import:: org.junit Before

.. java:import:: org.junit.runner RunWith

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.boot.test WebIntegrationTest

.. java:import:: org.springframework.test.context.junit4 SpringJUnit4ClassRunner

.. java:import:: java.io IOException

.. java:import:: java.time Instant

Datastore_IT
============

.. java:package:: net.es.oscars
   :noindex:

.. java:type:: @Slf4j @RunWith @WebIntegrationTest public class Datastore_IT

Methods
-------
prepare
^^^^^^^

.. java:method:: @Before public void prepare() throws IOException
   :outertype: Datastore_IT

