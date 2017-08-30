.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv BasicCircuitSpecification

.. java:import:: net.es.oscars.dto.resv CircuitSpecification

.. java:import:: net.es.oscars.dto.resv Connection

.. java:import:: net.es.oscars.dto.resv ReservationDetails

.. java:import:: net.es.oscars.oscarsapi RequestController

.. java:import:: net.es.oscars.simpleresv.svc ConnectionGenerationService

.. java:import:: net.es.oscars.simpleresv.svc ConnectionSimplificationService

.. java:import:: net.es.oscars.st.resv ResvState

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Controller

SimpleResvController
====================

.. java:package:: net.es.oscars.simpleresv.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class SimpleResvController

   Provides a simplified endpoint for submitting and committing circuit reservations.

Methods
-------
abort
^^^^^

.. java:method:: @RequestMapping @ResponseBody public ReservationDetails abort(String connectionId)
   :outertype: SimpleResvController

commit
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public ReservationDetails commit(String connectionId)
   :outertype: SimpleResvController

getDetails
^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public ReservationDetails getDetails(String connectionId)
   :outertype: SimpleResvController

submitCommitBasicSpec
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public ReservationDetails submitCommitBasicSpec(BasicCircuitSpecification spec)
   :outertype: SimpleResvController

submitCommitSpec
^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public ReservationDetails submitCommitSpec(CircuitSpecification spec)
   :outertype: SimpleResvController

submitSpec
^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public ReservationDetails submitSpec(CircuitSpecification spec)
   :outertype: SimpleResvController

