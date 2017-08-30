.. java:import:: com.fasterxml.jackson.databind.annotation JsonSerialize

.. java:import:: lombok Data

.. java:import:: net.es.oscars.conf.rest StartupCfgSerializer

.. java:import:: java.io Serializable

StartupConfigEntry
==================

.. java:package:: net.es.oscars.conf.prop
   :noindex:

.. java:type:: @Data @JsonSerialize public class StartupConfigEntry implements Serializable

Fields
------
name
^^^^

.. java:field::  String name
   :outertype: StartupConfigEntry

rest_password
^^^^^^^^^^^^^

.. java:field::  String rest_password
   :outertype: StartupConfigEntry

rest_truststore
^^^^^^^^^^^^^^^

.. java:field::  String rest_truststore
   :outertype: StartupConfigEntry

rest_username
^^^^^^^^^^^^^

.. java:field::  String rest_username
   :outertype: StartupConfigEntry

sec_basic_enabled
^^^^^^^^^^^^^^^^^

.. java:field::  Boolean sec_basic_enabled
   :outertype: StartupConfigEntry

sec_user_name
^^^^^^^^^^^^^

.. java:field::  String sec_user_name
   :outertype: StartupConfigEntry

sec_user_password
^^^^^^^^^^^^^^^^^

.. java:field::  String sec_user_password
   :outertype: StartupConfigEntry

server_port
^^^^^^^^^^^

.. java:field::  Integer server_port
   :outertype: StartupConfigEntry

ssl_ciphers
^^^^^^^^^^^

.. java:field::  String ssl_ciphers
   :outertype: StartupConfigEntry

ssl_enabled
^^^^^^^^^^^

.. java:field::  Boolean ssl_enabled
   :outertype: StartupConfigEntry

ssl_key_alias
^^^^^^^^^^^^^

.. java:field::  String ssl_key_alias
   :outertype: StartupConfigEntry

ssl_key_password
^^^^^^^^^^^^^^^^

.. java:field::  String ssl_key_password
   :outertype: StartupConfigEntry

ssl_key_store
^^^^^^^^^^^^^

.. java:field::  String ssl_key_store
   :outertype: StartupConfigEntry

ssl_key_store_password
^^^^^^^^^^^^^^^^^^^^^^

.. java:field::  String ssl_key_store_password
   :outertype: StartupConfigEntry

ssl_key_store_type
^^^^^^^^^^^^^^^^^^

.. java:field::  String ssl_key_store_type
   :outertype: StartupConfigEntry

Constructors
------------
StartupConfigEntry
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public StartupConfigEntry()
   :outertype: StartupConfigEntry

