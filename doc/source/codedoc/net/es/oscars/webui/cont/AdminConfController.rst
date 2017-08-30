.. java:import:: com.fasterxml.jackson.databind ObjectMapper

.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.cfg StartupConfig

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Controller

.. java:import:: org.springframework.ui Model

.. java:import:: org.springframework.web.bind.annotation ModelAttribute

.. java:import:: org.springframework.web.bind.annotation PathVariable

.. java:import:: org.springframework.web.bind.annotation RequestMapping

.. java:import:: org.springframework.web.bind.annotation RequestMethod

.. java:import:: org.springframework.web.client RestTemplate

.. java:import:: java.io IOException

AdminConfController
===================

.. java:package:: net.es.oscars.webui.cont
   :noindex:

.. java:type:: @Slf4j @Controller public class AdminConfController

Methods
-------
admin_comp_edit
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_comp_edit(String comp_name, Model model) throws IOException
   :outertype: AdminConfController

admin_comp_list
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_comp_list(Model model)
   :outertype: AdminConfController

admin_comp_update_submit
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_comp_update_submit(StartupConfig updatedConfig)
   :outertype: AdminConfController

