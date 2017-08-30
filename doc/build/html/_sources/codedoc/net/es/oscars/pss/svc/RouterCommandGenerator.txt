.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.pss.dao RouterCommandsRepository

.. java:import:: net.es.oscars.pss.ent RouterCommandsE

.. java:import:: net.es.oscars.resv.dao ConnectionRepository

.. java:import:: net.es.oscars.st.prov ProvState

.. java:import:: net.es.oscars.topo.ent UrnE

.. java:import:: net.es.oscars.topo.svc TopoService

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: org.springframework.transaction.annotation Transactional

RouterCommandGenerator
======================

.. java:package:: net.es.oscars.pss.svc
   :noindex:

.. java:type:: @Slf4j @Component @Transactional public class RouterCommandGenerator

Methods
-------
generateConfig
^^^^^^^^^^^^^^

.. java:method:: public void generateConfig(ConnectionE conn) throws PSSException
   :outertype: RouterCommandGenerator

