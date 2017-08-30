.. java:import:: com.fasterxml.jackson.core JsonGenerator

.. java:import:: com.fasterxml.jackson.core JsonProcessingException

.. java:import:: com.fasterxml.jackson.databind JsonSerializer

.. java:import:: com.fasterxml.jackson.databind SerializerProvider

.. java:import:: net.es.oscars.conf.prop StartupConfigEntry

.. java:import:: java.io IOException

StartupCfgSerializer
====================

.. java:package:: net.es.oscars.conf.rest
   :noindex:

.. java:type:: public class StartupCfgSerializer extends JsonSerializer<StartupConfigEntry>

Methods
-------
serialize
^^^^^^^^^

.. java:method:: @Override public void serialize(StartupConfigEntry value, JsonGenerator jgen, SerializerProvider provider) throws IOException
   :outertype: StartupCfgSerializer

