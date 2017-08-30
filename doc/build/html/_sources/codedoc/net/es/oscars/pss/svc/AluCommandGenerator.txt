.. java:import:: freemarker.template TemplateException

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv ResourceType

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.pss.dao UrnAddressRepository

.. java:import:: net.es.oscars.pss.ent UrnAddressE

.. java:import:: net.es.oscars.pss.tpl Assembler

.. java:import:: net.es.oscars.pss.tpl Stringifier

.. java:import:: net.es.oscars.topo.ent UrnE

.. java:import:: net.es.oscars.topo.svc TopoService

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

.. java:import:: java.io IOException

AluCommandGenerator
===================

.. java:package:: net.es.oscars.pss.svc
   :noindex:

.. java:type:: @Slf4j @Component public class AluCommandGenerator

Methods
-------
alcatelFixture
^^^^^^^^^^^^^^

.. java:method:: public AluFixtureParams alcatelFixture(ReservedVlanFixtureE rvf, ConnectionE conn) throws PSSException
   :outertype: AluCommandGenerator

ethPipe
^^^^^^^

.. java:method:: public String ethPipe(ReservedVlanJunctionE rvj, ReservedEthPipeE rep, List<String> ero) throws PSSException
   :outertype: AluCommandGenerator

isolatedJunction
^^^^^^^^^^^^^^^^

.. java:method:: public String isolatedJunction(ReservedVlanJunctionE rvj, ConnectionE conn) throws PSSException
   :outertype: AluCommandGenerator

mplsPipe
^^^^^^^^

.. java:method:: public String mplsPipe(ConnectionE conn, ReservedVlanJunctionE from, ReservedVlanJunctionE to, ReservedMplsPipeE rmp, List<String> ero) throws PSSException
   :outertype: AluCommandGenerator

