.. java:import:: freemarker.template TemplateException

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv Connection

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.pss.dao TemplateRepository

.. java:import:: net.es.oscars.pss.tpl Assembler

.. java:import:: net.es.oscars.pss.tpl Stringifier

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Component

JuniperMxCommandGenerator
=========================

.. java:package:: net.es.oscars.pss.svc
   :noindex:

.. java:type:: @Component @Slf4j public class JuniperMxCommandGenerator

Methods
-------
ethPipe
^^^^^^^

.. java:method:: public String ethPipe(ReservedVlanJunctionE rvj, ReservedEthPipeE rep, List<String> ero) throws PSSException
   :outertype: JuniperMxCommandGenerator

isolatedJunction
^^^^^^^^^^^^^^^^

.. java:method:: public String isolatedJunction(ReservedVlanJunctionE rvj, ConnectionE conn) throws PSSException
   :outertype: JuniperMxCommandGenerator

mplsPipe
^^^^^^^^

.. java:method:: public String mplsPipe(ConnectionE conn, ReservedVlanJunctionE from, ReservedVlanJunctionE to, ReservedMplsPipeE rmp, List<String> ero) throws PSSException
   :outertype: JuniperMxCommandGenerator

