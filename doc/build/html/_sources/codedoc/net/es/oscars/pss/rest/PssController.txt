.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.pss.dao RouterCommandsRepository

.. java:import:: net.es.oscars.pss.dao TemplateRepository

.. java:import:: net.es.oscars.pss.ent RouterCommandsE

.. java:import:: net.es.oscars.pss.ent TemplateE

.. java:import:: net.es.oscars.dto.pss RouterConfigTemplate

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.http HttpStatus

.. java:import:: org.springframework.stereotype Controller

.. java:import:: java.util.stream Collectors

PssController
=============

.. java:package:: net.es.oscars.pss.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class PssController

Methods
-------
byName
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public RouterConfigTemplate byName(String name)
   :outertype: PssController

commands
^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Map<String, String> commands(String connectionId, String deviceUrn)
   :outertype: PssController

getAll
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<String> getAll()
   :outertype: PssController

handleResourceNotFoundException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleResourceNotFoundException(NoSuchElementException ex)
   :outertype: PssController

