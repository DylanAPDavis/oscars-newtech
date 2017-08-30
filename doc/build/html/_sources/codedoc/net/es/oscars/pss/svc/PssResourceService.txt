.. java:import:: com.fasterxml.jackson.core JsonProcessingException

.. java:import:: com.fasterxml.jackson.databind ObjectMapper

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv ResourceType

.. java:import:: net.es.oscars.dto.spec ReservedBlueprint

.. java:import:: net.es.oscars.dto.spec ReservedMplsPipe

.. java:import:: net.es.oscars.dto.spec ReservedVlanFlow

.. java:import:: net.es.oscars.dto.spec ReservedVlanJunction

.. java:import:: net.es.oscars.dto.topo.enums DeviceModel

.. java:import:: net.es.oscars.pce TopPCE

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.pss.prop PssConfig

.. java:import:: net.es.oscars.pss.tpl Assembler

.. java:import:: net.es.oscars.pss.tpl Stringifier

.. java:import:: net.es.oscars.resv.dao ConnectionRepository

.. java:import:: net.es.oscars.resv.dao ReservedPssResourceRepository

.. java:import:: net.es.oscars.st.prov ProvState

.. java:import:: net.es.oscars.topo.ent UrnE

.. java:import:: net.es.oscars.topo.svc TopoService

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Service

.. java:import:: org.springframework.transaction.annotation Transactional

.. java:import:: java.time Instant

.. java:import:: java.util HashSet

.. java:import:: java.util List

.. java:import:: java.util Optional

.. java:import:: java.util Set

PssResourceService
==================

.. java:package:: net.es.oscars.pss.svc
   :noindex:

.. java:type:: @Service @Transactional @Slf4j public class PssResourceService

Methods
-------
generateConfig
^^^^^^^^^^^^^^

.. java:method:: public void generateConfig(ConnectionE conn) throws PSSException
   :outertype: PssResourceService

release
^^^^^^^

.. java:method:: public void release(ConnectionE conn)
   :outertype: PssResourceService

reserve
^^^^^^^

.. java:method:: public void reserve(ConnectionE conn)
   :outertype: PssResourceService

