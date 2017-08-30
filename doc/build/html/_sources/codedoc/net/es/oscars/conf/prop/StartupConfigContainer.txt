.. java:import:: lombok Data

.. java:import:: org.springframework.boot.context.properties ConfigurationProperties

.. java:import:: org.springframework.boot.context.properties NestedConfigurationProperty

.. java:import:: org.springframework.context.annotation Configuration

.. java:import:: java.util List

StartupConfigContainer
======================

.. java:package:: net.es.oscars.conf.prop
   :noindex:

.. java:type:: @Data @Configuration @ConfigurationProperties public class StartupConfigContainer

Fields
------
defaults
^^^^^^^^

.. java:field:: @NestedConfigurationProperty  StartupConfigEntry defaults
   :outertype: StartupConfigContainer

modules
^^^^^^^

.. java:field::  List<StartupConfigEntry> modules
   :outertype: StartupConfigContainer

Constructors
------------
StartupConfigContainer
^^^^^^^^^^^^^^^^^^^^^^

.. java:constructor:: public StartupConfigContainer()
   :outertype: StartupConfigContainer

