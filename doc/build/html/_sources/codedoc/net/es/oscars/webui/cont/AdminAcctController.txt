.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.acct Customer

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Controller

.. java:import:: org.springframework.ui Model

.. java:import:: org.springframework.web.bind.annotation ModelAttribute

.. java:import:: org.springframework.web.bind.annotation PathVariable

.. java:import:: org.springframework.web.bind.annotation RequestMapping

.. java:import:: org.springframework.web.bind.annotation RequestMethod

.. java:import:: org.springframework.web.client RestTemplate

AdminAcctController
===================

.. java:package:: net.es.oscars.webui.cont
   :noindex:

.. java:type:: @Slf4j @Controller public class AdminAcctController

Methods
-------
admin_comp_list
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_comp_list(Model model)
   :outertype: AdminAcctController

admin_cust_edit
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_cust_edit(String name, Model model)
   :outertype: AdminAcctController

admin_user_update_submit
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_update_submit(Customer updatedCustomer)
   :outertype: AdminAcctController

