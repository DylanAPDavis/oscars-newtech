.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv Connection

.. java:import:: net.es.oscars.dto.resv ConnectionFilter

.. java:import:: net.es.oscars.pce PCEException

.. java:import:: net.es.oscars.pss PSSException

.. java:import:: net.es.oscars.resv.ent ConnectionE

.. java:import:: net.es.oscars.resv.svc ResvService

.. java:import:: net.es.oscars.st.resv ResvState

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.dao DataIntegrityViolationException

.. java:import:: org.springframework.http HttpStatus

.. java:import:: org.springframework.stereotype Controller

ResvController
==============

.. java:package:: net.es.oscars.resv.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class ResvController

Constructors
------------
ResvController
^^^^^^^^^^^^^^

.. java:constructor:: @Autowired public ResvController(ResvService resvService)
   :outertype: ResvController

Methods
-------
abort
^^^^^

.. java:method:: @RequestMapping @ResponseBody public Connection abort(String connectionId)
   :outertype: ResvController

allResvs
^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<Connection> allResvs()
   :outertype: ResvController

commit
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Connection commit(String connectionId)
   :outertype: ResvController

getResv
^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Connection getResv(String connectionId)
   :outertype: ResvController

handleDataIntegrityViolationException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleDataIntegrityViolationException(DataIntegrityViolationException ex)
   :outertype: ResvController

handleResourceNotFoundException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleResourceNotFoundException(NoSuchElementException ex)
   :outertype: ResvController

preCheck
^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Connection preCheck(Connection connection) throws PSSException, PCEException
   :outertype: ResvController

resvFilter
^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Set<Connection> resvFilter(ConnectionFilter filter)
   :outertype: ResvController

submitConnection
^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Connection submitConnection(Connection connection) throws PSSException, PCEException
   :outertype: ResvController

