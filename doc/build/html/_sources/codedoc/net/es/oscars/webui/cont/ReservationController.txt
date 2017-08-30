.. java:import:: com.fasterxml.jackson.databind ObjectMapper

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.bwavail PortBandwidthAvailabilityRequest

.. java:import:: net.es.oscars.dto.bwavail PortBandwidthAvailabilityResponse

.. java:import:: net.es.oscars.dto.resv Connection

.. java:import:: net.es.oscars.dto.resv ConnectionFilter

.. java:import:: net.es.oscars.dto.spec RequestedVlanPipe

.. java:import:: net.es.oscars.dto.topo BidirectionalPath

.. java:import:: net.es.oscars.dto.topo Edge

.. java:import:: net.es.oscars.webui.dto MinimalRequest

.. java:import:: net.es.oscars.webui.ipc ConnectionProvider

.. java:import:: net.es.oscars.webui.ipc MinimalPreChecker

.. java:import:: net.es.oscars.webui.ipc MinimalRequester

.. java:import:: org.hashids Hashids

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Controller

.. java:import:: org.springframework.ui Model

.. java:import:: org.springframework.web.client RestTemplate

ReservationController
=====================

.. java:package:: net.es.oscars.webui.cont
   :noindex:

.. java:type:: @Slf4j @Controller public class ReservationController

Methods
-------
commands
^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Map<String, String> commands(String connectionId, String deviceUrn)
   :outertype: ReservationController

connection_commit
^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String connection_commit(String connectionId, Model model)
   :outertype: ReservationController

connection_commit_react
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public String connection_commit_react(String connectionId)
   :outertype: ReservationController

new_connection_id
^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Map<String, String> new_connection_id()
   :outertype: ReservationController

queryPortBwAvailability
^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public PortBandwidthAvailabilityResponse queryPortBwAvailability(MinimalRequest request)
   :outertype: ReservationController

resv_gui
^^^^^^^^

.. java:method:: @RequestMapping public String resv_gui(Model model)
   :outertype: ReservationController

resv_list
^^^^^^^^^

.. java:method:: @RequestMapping public String resv_list(Model model)
   :outertype: ReservationController

resv_list_connections
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Set<Connection> resv_list_connections()
   :outertype: ReservationController

resv_minimal_hold
^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Map<String, String> resv_minimal_hold(MinimalRequest request)
   :outertype: ReservationController

resv_preCheck
^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Map<String, String> resv_preCheck(MinimalRequest request)
   :outertype: ReservationController

resv_timebar
^^^^^^^^^^^^

.. java:method:: @RequestMapping public String resv_timebar(Model model)
   :outertype: ReservationController

resv_view
^^^^^^^^^

.. java:method:: @RequestMapping public String resv_view(String connectionId, Model model)
   :outertype: ReservationController

