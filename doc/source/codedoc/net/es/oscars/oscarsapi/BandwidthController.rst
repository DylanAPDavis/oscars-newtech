.. java:import:: lombok.extern.slf4j Slf4j

.. java:import:: net.es.oscars.dto.bwavail BandwidthAvailabilityRequest

.. java:import:: net.es.oscars.dto.bwavail BandwidthAvailabilityResponse

.. java:import:: net.es.oscars.dto.bwavail PortBandwidthAvailabilityRequest

.. java:import:: net.es.oscars.dto.bwavail PortBandwidthAvailabilityResponse

.. java:import:: org.springframework.beans.factory.annotation Autowired

.. java:import:: org.springframework.stereotype Controller

.. java:import:: org.springframework.web.bind.annotation ModelAttribute

.. java:import:: org.springframework.web.client RestTemplate

.. java:import:: java.util HashMap

BandwidthController
===================

.. java:package:: net.es.oscars.oscarsapi
   :noindex:

.. java:type:: @Slf4j @Controller public class BandwidthController

Methods
-------
getBandwidthPath
^^^^^^^^^^^^^^^^

.. java:method:: public BandwidthAvailabilityResponse getBandwidthPath(BandwidthAvailabilityRequest request)
   :outertype: BandwidthController

getBandwidthPorts
^^^^^^^^^^^^^^^^^

.. java:method:: public PortBandwidthAvailabilityResponse getBandwidthPorts(PortBandwidthAvailabilityRequest request)
   :outertype: BandwidthController

handlePathQueryException
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public BandwidthAvailabilityResponse handlePathQueryException()
   :outertype: BandwidthController

handlePortQueryException
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public PortBandwidthAvailabilityResponse handlePortQueryException()
   :outertype: BandwidthController

