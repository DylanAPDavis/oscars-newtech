.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.conf.dao ConfigRepository

.. java:import:: net.es.oscars.conf.ent EStartupConfig

.. java:import:: net.es.oscars.dto.cfg StartupConfig

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.dao DataIntegrityViolationException

.. java:import:: org.springframework.http HttpStatus

.. java:import:: org.springframework.stereotype Controller

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util NoSuchElementException

.. java:import:: java.util Optional

.. java:import:: java.util.stream Collectors

ConfigController
================

.. java:package:: net.es.oscars.conf.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class ConfigController

Constructors
------------
ConfigController
^^^^^^^^^^^^^^^^

.. java:constructor:: @Autowired public ConfigController(ConfigRepository repository)
   :outertype: ConfigController

Methods
-------
delConfig
^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public String delConfig(String component)
   :outertype: ConfigController

getConfig
^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public String getConfig(String component)
   :outertype: ConfigController

handleDataIntegrityViolationException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleDataIntegrityViolationException(DataIntegrityViolationException ex)
   :outertype: ConfigController

handleResourceNotFoundException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleResourceNotFoundException(NoSuchElementException ex)
   :outertype: ConfigController

listComponents
^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<String> listComponents()
   :outertype: ConfigController

update
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public StartupConfig update(StartupConfig startupConfig)
   :outertype: ConfigController

