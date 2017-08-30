.. java:import:: net.es.oscars.pss.ent TemplateE

.. java:import:: org.springframework.data.repository CrudRepository

.. java:import:: org.springframework.stereotype Repository

.. java:import:: java.util List

.. java:import:: java.util Optional

TemplateRepository
==================

.. java:package:: net.es.oscars.pss.dao
   :noindex:

.. java:type:: @Repository public interface TemplateRepository extends CrudRepository<TemplateE, Long>

Methods
-------
findAll
^^^^^^^

.. java:method::  List<TemplateE> findAll()
   :outertype: TemplateRepository

findByName
^^^^^^^^^^

.. java:method::  Optional<TemplateE> findByName(String name)
   :outertype: TemplateRepository

