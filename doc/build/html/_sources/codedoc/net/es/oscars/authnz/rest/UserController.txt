.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.authnz.dao UserRepository

.. java:import:: net.es.oscars.authnz.ent EUser

.. java:import:: net.es.oscars.dto.auth Permissions

.. java:import:: net.es.oscars.dto.auth User

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.dao DataIntegrityViolationException

.. java:import:: org.springframework.http HttpStatus

.. java:import:: org.springframework.stereotype Controller

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util NoSuchElementException

UserController
==============

.. java:package:: net.es.oscars.authnz.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class UserController

Constructors
------------
UserController
^^^^^^^^^^^^^^

.. java:constructor:: @Autowired public UserController(UserRepository userRepo)
   :outertype: UserController

Methods
-------
add
^^^

.. java:method:: @RequestMapping @ResponseBody public User add(User dtoUser)
   :outertype: UserController

byCertSubject
^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public User byCertSubject(String certSubject)
   :outertype: UserController

byUsername
^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public User byUsername(String username)
   :outertype: UserController

delete
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public String delete(String username)
   :outertype: UserController

getAll
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<User> getAll()
   :outertype: UserController

getInstitutions
^^^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<String> getInstitutions()
   :outertype: UserController

handleDataIntegrityViolationException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleDataIntegrityViolationException(DataIntegrityViolationException ex)
   :outertype: UserController

handleResourceNotFoundException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleResourceNotFoundException(NoSuchElementException ex)
   :outertype: UserController

update
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public User update(User dtoUser)
   :outertype: UserController

