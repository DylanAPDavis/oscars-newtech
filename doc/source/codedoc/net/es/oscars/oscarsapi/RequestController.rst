.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.resv Connection

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Controller

.. java:import:: org.springframework.web.bind.annotation ModelAttribute

.. java:import:: org.springframework.web.bind.annotation PathVariable

.. java:import:: org.springframework.web.client RestTemplate

RequestController
=================

.. java:package:: net.es.oscars.oscarsapi
   :noindex:

.. java:type:: @Slf4j @Controller public class RequestController

Methods
-------
abort
^^^^^

.. java:method:: public Connection abort(String connectionId)
   :outertype: RequestController

commit
^^^^^^

.. java:method:: public Connection commit(String connectionId)
   :outertype: RequestController

handleException
^^^^^^^^^^^^^^^

.. java:method:: public Connection handleException(Exception e, String action, String connectionId)
   :outertype: RequestController

query
^^^^^

.. java:method:: public Connection query(String connectionId)
   :outertype: RequestController

submit
^^^^^^

.. java:method:: public Connection submit(Connection conn)
   :outertype: RequestController

