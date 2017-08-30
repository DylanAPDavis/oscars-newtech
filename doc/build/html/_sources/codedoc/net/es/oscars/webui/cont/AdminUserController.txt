.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.auth User

.. java:import:: net.es.oscars.webui RestAuthProvider

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Controller

.. java:import:: org.springframework.ui Model

.. java:import:: org.springframework.web.bind.annotation ModelAttribute

.. java:import:: org.springframework.web.bind.annotation PathVariable

.. java:import:: org.springframework.web.bind.annotation RequestMapping

.. java:import:: org.springframework.web.bind.annotation RequestMethod

.. java:import:: org.springframework.web.client RestTemplate

AdminUserController
===================

.. java:package:: net.es.oscars.webui.cont
   :noindex:

.. java:type:: @Slf4j @Controller public class AdminUserController

Methods
-------
admin_user_add
^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_add(Model model)
   :outertype: AdminUserController

admin_user_add_submit
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_add_submit(User addedUser)
   :outertype: AdminUserController

admin_user_del_submit
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_del_submit(User userToDelete)
   :outertype: AdminUserController

admin_user_edit
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_edit(String username, Model model)
   :outertype: AdminUserController

admin_user_list
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_list(Model model)
   :outertype: AdminUserController

admin_user_pwd_submit
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_pwd_submit(User updatedUser)
   :outertype: AdminUserController

admin_user_update_submit
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping public String admin_user_update_submit(User updatedUser)
   :outertype: AdminUserController

