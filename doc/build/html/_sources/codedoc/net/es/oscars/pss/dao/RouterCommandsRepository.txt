.. java:import:: net.es.oscars.pss.ent RouterCommandsE

.. java:import:: net.es.oscars.pss.ent TemplateE

.. java:import:: org.springframework.data.repository CrudRepository

.. java:import:: org.springframework.stereotype Repository

.. java:import:: java.util List

.. java:import:: java.util Optional

RouterCommandsRepository
========================

.. java:package:: net.es.oscars.pss.dao
   :noindex:

.. java:type:: @Repository public interface RouterCommandsRepository extends CrudRepository<RouterCommandsE, Long>

Methods
-------
findAll
^^^^^^^

.. java:method::  List<RouterCommandsE> findAll()
   :outertype: RouterCommandsRepository

findByConnectionIdAndDeviceUrn
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method::  Optional<RouterCommandsE> findByConnectionIdAndDeviceUrn(String connectionId, String deviceUrn)
   :outertype: RouterCommandsRepository

