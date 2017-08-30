.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: org.springframework.stereotype Service

.. java:import:: java.time Instant

.. java:import:: java.time LocalDateTime

.. java:import:: java.time ZoneId

.. java:import:: java.time.format DateTimeFormatter

.. java:import:: java.util Date

DateService
===========

.. java:package:: net.es.oscars.simpleresv.svc
   :noindex:

.. java:type:: @Service @Slf4j public class DateService

Methods
-------
convertDateToString
^^^^^^^^^^^^^^^^^^^

.. java:method:: public String convertDateToString(Date date)
   :outertype: DateService

convertInsantToString
^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public String convertInsantToString(Instant instant)
   :outertype: DateService

parseDate
^^^^^^^^^

.. java:method:: public Date parseDate(String timeString)
   :outertype: DateService

