.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.acct.ent CustomerE

.. java:import:: net.es.oscars.acct.svc CustService

.. java:import:: net.es.oscars.dto.acct Customer

.. java:import:: org.modelmapper ModelMapper

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.dao DataIntegrityViolationException

.. java:import:: org.springframework.http HttpStatus

.. java:import:: org.springframework.stereotype Controller

.. java:import:: java.util ArrayList

.. java:import:: java.util List

.. java:import:: java.util NoSuchElementException

AcctController
==============

.. java:package:: net.es.oscars.acct.rest
   :noindex:

.. java:type:: @Slf4j @Controller public class AcctController

Constructors
------------
AcctController
^^^^^^^^^^^^^^

.. java:constructor:: @Autowired public AcctController(CustService custService)
   :outertype: AcctController

Methods
-------
custByName
^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Customer custByName(String name)
   :outertype: AcctController

delete
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public String delete(String name)
   :outertype: AcctController

handleDataIntegrityViolationException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleDataIntegrityViolationException(DataIntegrityViolationException ex)
   :outertype: AcctController

handleResourceNotFoundException
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: @ExceptionHandler @ResponseStatus public void handleResourceNotFoundException(NoSuchElementException ex)
   :outertype: AcctController

listCustomers
^^^^^^^^^^^^^

.. java:method:: @RequestMapping @ResponseBody public List<Customer> listCustomers()
   :outertype: AcctController

update
^^^^^^

.. java:method:: @RequestMapping @ResponseBody public Customer update(Customer dtoCustomer)
   :outertype: AcctController

