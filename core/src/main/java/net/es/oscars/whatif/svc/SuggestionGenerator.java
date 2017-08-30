package net.es.oscars.whatif.svc;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.bwavail.svc.BandwidthAvailabilityGenerationService;
import net.es.oscars.bwavail.svc.BandwidthAvailabilityService;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityRequest;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityResponse;
import net.es.oscars.dto.resv.Connection;
import net.es.oscars.pce.exc.PCEException;
import net.es.oscars.pss.PSSException;
import net.es.oscars.resv.rest.ResvController;
import net.es.oscars.resv.svc.ConnectionGenerationService;
import net.es.oscars.resv.svc.DateService;
import net.es.oscars.whatif.dto.WhatifSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class SuggestionGenerator {


    ConnectionGenerationService connectionGenerationService;

    ResvController resvController;

    DateService dateService;

    BandwidthAvailabilityGenerationService bwAvailGenService;

    BandwidthAvailabilityService bwAvailService;

    @Autowired
    public SuggestionGenerator(ConnectionGenerationService connectionGenerationService, ResvController resvController, DateService dateService,
                               BandwidthAvailabilityGenerationService bwAvailGenService, BandwidthAvailabilityService bwAvailService) {
        this.connectionGenerationService = connectionGenerationService;
        this.resvController = resvController;
        this.dateService = dateService;
        this.bwAvailGenService = bwAvailGenService;
        this.bwAvailService = bwAvailService;
    }

    private Date addTime(Date d, Integer increment, Integer amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(increment, amount);
        return cal.getTime();
    }

    private void testAndAddConnection(List<Connection> connections, Connection conn) {

        // Run a precheck
        Connection result = null;
        try {
            result = resvController.preCheck(conn);
            if(result != null){
                // Determine if result is successful. If so, consider storing it as an option
                if(result.getReserved().getVlanFlow().getAllPaths().size() > 0){
                    // Success!  Now we can add this connection to our list
                    connections.add(result);
                }
            }
        }
        catch(PCEException |PSSException e){
            log.info("Connection precheck caused an exception.");
        }
    }



    public List<Connection> generateStartEndWindowsVolume(WhatifSpecification spec, String earliestStartStr, String latestStartStr, String earliestDeadlineStr, String latestDeadlineStr) {
        List<Connection> connections = new ArrayList<>();
        List<Date> possibleStartTimes = new ArrayList<>();
        List<Date> possibleEndTimes = new ArrayList<>();
        List<Integer> possibleBandwidths = new ArrayList<>();

        Integer volume = spec.getVolume();
        Date earliestStart = dateService.parseDate(earliestStartStr);
        Date latestStart = dateService.parseDate(latestStartStr);
        Date earliestDeadline = dateService.parseDate(earliestDeadlineStr);
        Date latestDeadline = dateService.parseDate(latestDeadlineStr);

        // Creating map from earliest start to latest deadline
        BandwidthAvailabilityRequest bwAvailRequest = createBwAvailRequest(spec.getSrcDevice(), spec.getSrcPorts(),
                spec.getDstDevice(), spec.getDstPorts(), 0, 0, earliestStart, latestDeadline);
        BandwidthAvailabilityResponse bwResponse = bwAvailService.getBandwidthAvailabilityMap(bwAvailRequest);

        Map<Instant, Integer> bwMap = bwResponse.getBwAvailabilityMap().get("Az1");

        // Creating map of possible start times
        BandwidthAvailabilityRequest bwAvailRequestStarts = createBwAvailRequest(spec.getSrcDevice(), spec.getSrcPorts(),
                spec.getDstDevice(), spec.getDstPorts(), 0, 0, earliestStart, latestStart);
        BandwidthAvailabilityResponse bwResponseStarts = bwAvailService.getBandwidthAvailabilityMap(bwAvailRequestStarts);

        Map<Instant, Integer> bwMapStarts = bwResponseStarts.getBwAvailabilityMap().get("Az1");

        // Creating map of possible end times
        BandwidthAvailabilityRequest bwAvailRequestDeadlines = createBwAvailRequest(spec.getSrcDevice(), spec.getSrcPorts(),
                spec.getDstDevice(), spec.getDstPorts(), 0, 0, earliestDeadline, latestDeadline);
        BandwidthAvailabilityResponse bwResponseDeadlines = bwAvailService.getBandwidthAvailabilityMap(bwAvailRequestDeadlines);

        Map<Instant, Integer> bwMapDeadlines = bwResponseDeadlines.getBwAvailabilityMap().get("Az1");


        // Now sort all three lists of times
        List<Instant> times = new ArrayList<Instant>();
        times.addAll(bwMap.keySet());
        Collections.sort(times, new Comparator<Instant>() {
            @Override
            public int compare(Instant o1, Instant o2) {
                return o1.compareTo(o2);
            }
        });

        List<Instant> startTimes = new ArrayList<Instant>();
        startTimes.addAll(bwMapDeadlines.keySet());
        Collections.sort(startTimes, new Comparator<Instant>() {
            @Override
            public int compare(Instant o1, Instant o2) {
                return o1.compareTo(o2);
            }
        });

        List<Instant> endTimes = new ArrayList<Instant>();
        endTimes.addAll(bwMapDeadlines.keySet());
        Collections.sort(endTimes, new Comparator<Instant>() {
            @Override
            public int compare(Instant o1, Instant o2) {
                return o1.compareTo(o2);
            }
        });

        for(Instant start : startTimes) {
            for(Instant end : endTimes) {
                if(Date.from(start).compareTo(Date.from(end)) >= 0) {
                    // The start time is either AFTER or EQUAL to the end time, so we should continue to the next end time
                    continue;
                }

                Integer minimumBandwidth = null;
                Integer previousBandwidth = null;
                Boolean optionOver = false;

                for(Instant instant : times) {
                    if(Date.from(start).compareTo(Date.from(instant)) > 0) {
                        // This instant is not in the start -> end window, so continue to the next instant

                        // We will however note the bandwidth at this time because this amount of bandwidth may
                        // bleed into our start -> end window

                        previousBandwidth = bwMap.get(instant);

                        continue;
                    }
                    if(Date.from(instant).compareTo(Date.from(end)) >= 0) {
                        // This instant is either AFTER or EQUAL to our end time
                        // Either way we will be checking the end time before stopping
                        instant = end;
                        optionOver = true;
                    }
                    if(minimumBandwidth == null && Date.from(start).compareTo(Date.from(instant)) < 0) {
                        // The start -> end window did not align perfectly inside our map
                        // We must keep in mind the 'previousBandwidth' value as it IS bleeding into our window
                        minimumBandwidth = previousBandwidth;
                    }

                    Integer bandwidth = bwMap.get(instant);

                    // We will make sure our minimumBandwidth has a value
                    if (minimumBandwidth == null) {
                        minimumBandwidth = bandwidth;
                    }

                    // Now we can determine if the allocation is possible at this point
                    Integer secondsBetween = (int) (( Date.from(instant).getTime() - Date.from(start).getTime()) / 1000);
                    Integer bandwidthNeeded = (int) Math.ceil(1.0 * volume / secondsBetween);

                    if(bandwidthNeeded <= minimumBandwidth) {
                        // Possible connection found!
                        // We will first determine when the earliest we can finish by is
                        Integer optionBandwidth = minimumBandwidth;
                        Integer durationSec = (int) (Math.ceil(1.0 * volume / optionBandwidth));
                        Date optionStart = Date.from(start);
                        Date optionEnd = addTime(optionStart, Calendar.SECOND, durationSec);
                        if(optionEnd.compareTo(earliestDeadline) < 0) {
                            // This recalculated end time went out of our end window
                            optionEnd = earliestDeadline;
                            secondsBetween = (int) (( optionEnd.getTime() - optionStart.getTime()) / 1000);
                            optionBandwidth = (int) Math.ceil(1.0 * volume / secondsBetween);
                        }
                        // We should keep track of the start, end, and bandwidth for later checking
                        possibleStartTimes.add(optionStart);
                        possibleEndTimes.add(optionEnd);
                        possibleBandwidths.add(optionBandwidth);

                        // Now that the best option in this specific window has been found, we can break and try a new window.
                        break;
                    }
                }
            }
        }

        for(int i = 0; i < possibleBandwidths.size(); i++) {
            Date start = possibleStartTimes.get(i);
            Date end = possibleEndTimes.get(i);
            Integer bandwidth = possibleBandwidths.get(i);

            // Each connection must have a unique id
            String connectionId = "startEndWindows" + i;

            // Create an initial connection from parameters
            Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                    spec.getDstPorts(), bandwidth, bandwidth, connectionId, start, end);

            testAndAddConnection(connections, conn);
        }

        return connections;
    }



    /**
     * Generate a list of viable connections given a Start Date, End Date, and requested transfer Volume.
     * Provide the minimum needed bandwidth, and the maximum possible.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithStartEndVolume(WhatifSpecification spec){
        List<Connection> connections = new ArrayList<>();
        Date start = dateService.parseDate(spec.getStartDate());
        Date end = dateService.parseDate(spec.getEndDate());
        Integer volume = spec.getVolume();

        // Determine these values - Bandwidth from src -> dst (a -> z), and from dst -> src (z -> a)
        // Values may be the same, or different
        Integer secondsBetween = (int) ((end.getTime() - start.getTime()) / 1000);
        Integer minimumRequiredBandwidth = (int) Math.ceil(1.0 * volume / secondsBetween);

        // Get the bandwidth availability along a path from srcDevice to dstDevice
        BandwidthAvailabilityRequest bwAvailRequest = createBwAvailRequest(spec.getSrcDevice(), spec.getSrcPorts(),
                spec.getDstDevice(), spec.getDstPorts(), minimumRequiredBandwidth, minimumRequiredBandwidth, start, end);
        BandwidthAvailabilityResponse bwResponse = bwAvailService.getBandwidthAvailabilityMap(bwAvailRequest);

        Map<Instant, Integer> bwMap = bwResponse.getBwAvailabilityMap().get("Az1");
        List<Integer> possibleBandwidths = new ArrayList<>();
        Integer minimumBandwidth = null;

        // Go through all critical points in the bandwidth availability map
        // Confirm that the available bandwidth does not drop below our minimum required
        for(Instant instant : bwMap.keySet()) {
            Integer bandwidth = bwMap.get(instant);
            if(minimumBandwidth == null || bandwidth < minimumBandwidth) {
                minimumBandwidth = bandwidth;
            }
            if(bandwidth < minimumRequiredBandwidth) {
                return connections;  // OSCARS will generate results
            }
        }

        // If the program arrived at this point in the code, we have two possible values for bandwidth
        // The first is the minimum required
        if(minimumRequiredBandwidth > 0) {
            possibleBandwidths.add(minimumRequiredBandwidth);
        }
        // The second, if it is not the same as the previous bandwidth value,
        // is the minimum bandwidth found on the bandwidth availability map
        if(minimumBandwidth != minimumRequiredBandwidth) {
            possibleBandwidths.add(minimumBandwidth);
        }

        // Now we can create a connection for all of the possible bandwidths
        for(int i = 0; i < possibleBandwidths.size(); i++) {

            Integer band = possibleBandwidths.get(i);

            // For now, both directions on the path will have equal bandwidth
            // It is possible we will want to change this in the future
            Integer azMbps = band;
            Integer zaMbps = band;

            // Each connection must have a unique id
            String connectionId = "startEndVolume" + i;

            // Create an initial connection from parameters
            Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                    spec.getDstPorts(), azMbps, zaMbps, connectionId, start, end);

            testAndAddConnection(connections, conn);
        }


        return connections;
    }

    /**
     * Generate a list of viable connections given a Start Date, and End Date.
     * Get the maximum bandwidth possible.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithStartEnd(WhatifSpecification spec) {
        List<Connection> connections = new ArrayList<>();
        Date start = dateService.parseDate(spec.getStartDate());
        Date end = dateService.parseDate(spec.getEndDate());

        BandwidthAvailabilityRequest bwAvailRequest = createBwAvailRequest(spec.getSrcDevice(), spec.getSrcPorts(),
                spec.getDstDevice(), spec.getDstPorts(), 0, 0, start, end);
        BandwidthAvailabilityResponse bwResponse = bwAvailService.getBandwidthAvailabilityMap(bwAvailRequest);

        Map<Instant, Integer> bwMap = bwResponse.getBwAvailabilityMap().get("Az1");
        Integer minimumBandwidth = null;

        // Go through all critical points in the bandwidth availability map
        // Calculate the minimum bandwidth available across the map
        for(Instant instant : bwMap.keySet()) {
            Integer bandwidth = bwMap.get(instant);
            if (minimumBandwidth == null || bandwidth < minimumBandwidth) {
                minimumBandwidth = bandwidth;
                if(minimumBandwidth == 0) {
                    return connections;
                }
            }
        }

        // For now, both directions on the path will have equal bandwidth
        // It is possible we will want to change this in the future
        Integer azMbps = minimumBandwidth;
        Integer zaMbps = minimumBandwidth;

        // Each connection must have a unique id
        String connectionId = "startEnd0";

        // Create an initial connection from parameters
        Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                spec.getDstPorts(), azMbps, zaMbps, connectionId, start, end);

        testAndAddConnection(connections, conn);

        return connections;
    }

    /**
     * Generate a list of viable connections given a Start Date, and requested transfer volume.
     * Complete as early as possible.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithStartVolume(WhatifSpecification spec, List<Integer> otherOptions) {

        List<Connection> connections = new ArrayList<>();
        Integer volume = spec.getVolume();
        Date start = dateService.parseDate(spec.getStartDate());

        // The end time will be set two years in the future to obtain
        // the largest bandwidth availability map possible
        Date end = addTime(start, Calendar.YEAR, 2);

        BandwidthAvailabilityRequest bwAvailRequest = createBwAvailRequest(spec.getSrcDevice(), spec.getSrcPorts(),
                spec.getDstDevice(), spec.getDstPorts(), 0, 0, start, end);
        BandwidthAvailabilityResponse bwResponse = bwAvailService.getBandwidthAvailabilityMap(bwAvailRequest);

        Map<Instant, Integer> bwMap = bwResponse.getBwAvailabilityMap().get("Az1");

        // Now we will sort the keys from the bandwidth availability map in chronological order
        List<Instant> times = new ArrayList<Instant>();
        times.addAll(bwMap.keySet());
        Collections.sort(times, new Comparator<Instant>() {
            @Override
            public int compare(Instant o1, Instant o2) {
                return o1.compareTo(o2);
            }
        });


        Integer minimumBandwidth = null;
        Integer bestOptionBandwidth = null;

        // Go through all critical points in the bandwidth availability map
        // Calculate the minimum bandwidth available across the map
        for(Instant instant : times) {
            Integer bandwidth = bwMap.get(instant);

            if(minimumBandwidth == null) {
                minimumBandwidth = bandwidth;
            }

            // Now we can check if we have enough bandwidth and time to perform the user's allocation
            Integer secondsBetween = (int) (( Date.from(instant).getTime() - start.getTime()) / 1000);
            Integer bandwidthNeeded = (int) Math.ceil(1.0 * volume / secondsBetween);

            if(bandwidthNeeded <= minimumBandwidth) {
                // If so, create the "best option" connection
                bestOptionBandwidth = minimumBandwidth;
                Integer durationSec = (int) (Math.ceil(1.0 * volume / minimumBandwidth));
                Date bestOptionEndTime = addTime(start, Calendar.SECOND, durationSec);

                // Create an initial connection from parameters
                Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                        spec.getDstPorts(), bestOptionBandwidth, bestOptionBandwidth, "startVolumeBest", start, bestOptionEndTime);

                testAndAddConnection(connections, conn);
                break;
            }

            if (bandwidth < minimumBandwidth) {
                minimumBandwidth = bandwidth;
                if(minimumBandwidth == 0) {
                    return connections;
                }
            }
        }

        // If the loop did not find a single possible option, return the empty list of connections
        if(bestOptionBandwidth == null) return connections;

        // Sort the other options in descending order
        // This will allow us to check the highest possible bandwidth options first
        Collections.sort(otherOptions, Collections.reverseOrder());

        List<Integer> invalidOptions = new ArrayList<Integer>();

        for(Integer i = 0; i < otherOptions.size(); i++) {
            Integer option = otherOptions.get(i);
            Integer bandwidthNeeded = (int) (option / 100.0 * bestOptionBandwidth);
            Integer durationSec = (int) (Math.ceil(1.0 * volume / bandwidthNeeded));
            Date optionEnd = addTime(start, Calendar.SECOND, durationSec);
            minimumBandwidth = null;

            for(Instant instant : times) {
                Integer bandwidth = bwMap.get(instant);
                if (minimumBandwidth == null) {
                    minimumBandwidth = bandwidth;
                }
                // Check if we have gone past the point where the current allocation will end
                if(Date.from(instant).compareTo(optionEnd) > 0) {
                    if(minimumBandwidth < bandwidthNeeded) {
                        invalidOptions.add(i);
                    }
                    else {
                        // This is a valid option!  Now attempt to create a connection
                        // Create an initial connection from parameters
                        Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                                spec.getDstPorts(), bandwidthNeeded, bandwidthNeeded, "startVolumeOption" + i, start, optionEnd);

                        testAndAddConnection(connections, conn);
                    }
                    break;
                }

                if (minimumBandwidth == null || bandwidth < minimumBandwidth) {
                    minimumBandwidth = bandwidth;
                    if(minimumBandwidth == 0) {
                        return connections;
                    }
                }
            }
        }

        // Now we must go through all invalid options and attempt to correct them

        for(Integer optionIndex : invalidOptions) {
            Integer option = otherOptions.get(optionIndex);
            Integer bandwidthNeeded = (int) (Math.ceil(option / 100.0 * bestOptionBandwidth));
            Integer durationSec = (int) (Math.ceil(1.0 * volume / bandwidthNeeded));
            Date optionEnd = addTime(start, Calendar.SECOND, durationSec);
            minimumBandwidth = null;

            // Determine the "neighbor" bandwidth (the option immediately after the current one)
            Integer neighborBandwidth = null;
            if(optionIndex + 1 < otherOptions.size()) {
                neighborBandwidth = (int) (otherOptions.get(optionIndex + 1) / 100.0 * bestOptionBandwidth);
            }

            for(Instant instant : times) {
                Integer bandwidth = bwMap.get(instant);

                if (minimumBandwidth == null) {
                    minimumBandwidth = bandwidth;
                }
                // Check if we have gone past the point where the current allocation will end
                if(Date.from(instant).compareTo(optionEnd) > 0) {
                    if(minimumBandwidth < bandwidthNeeded) {
                        bandwidthNeeded = minimumBandwidth;
                        if(neighborBandwidth == null || bandwidthNeeded < neighborBandwidth) {
                            break; // This option is "dead"
                        }
                        // Recalculate our end time given the new bandwidth value
                        durationSec = (int) (Math.ceil(1.0 * volume / bandwidthNeeded));
                        optionEnd = addTime(start, Calendar.SECOND, durationSec);
                    }
                    else {
                        // This option has been successfully reworked!  Now attempt to create a connection
                        // Create an initial connection from parameters
                        Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                                spec.getDstPorts(), bandwidthNeeded, bandwidthNeeded, "startVolumeOption(adjusted)" + optionIndex, start, optionEnd);

                        testAndAddConnection(connections, conn);
                        break;
                    }
                }

                if (minimumBandwidth == null || bandwidth < minimumBandwidth) {
                    minimumBandwidth = bandwidth;
                    if (minimumBandwidth == 0) {
                        return connections;
                    }
                }
            }
        }

        return connections;
    }

    /**
     * Generate a list of viable connections given an End Date, and requested transfer volume.
     * Complete transfer by deadline.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithEndVolume(WhatifSpecification spec, List<Integer> otherOptions) {
        List<Connection> connections = new ArrayList<>();
        Integer volume = spec.getVolume();
        Date end = dateService.parseDate(spec.getEndDate());

        // The start time will be the current date and time
        Date start = Calendar.getInstance().getTime();

        BandwidthAvailabilityRequest bwAvailRequest = createBwAvailRequest(spec.getSrcDevice(), spec.getSrcPorts(),
                spec.getDstDevice(), spec.getDstPorts(), 0, 0, start, end);
        BandwidthAvailabilityResponse bwResponse = bwAvailService.getBandwidthAvailabilityMap(bwAvailRequest);

        Map<Instant, Integer> bwMap = bwResponse.getBwAvailabilityMap().get("Az1");

        // Now we will sort the keys from the bandwidth availability map in reverse chronological order
        List<Instant> times = new ArrayList<Instant>();
        times.addAll(bwMap.keySet());
        Collections.sort(times, new Comparator<Instant>() {
            @Override
            public int compare(Instant o1, Instant o2) {
                return o2.compareTo(o1);
            }
        });

        Integer minimumBandwidth = null;
        Integer bestOptionBandwidth = null;

        // Go through all critical points in the bandwidth availability map
        // Calculate the minimum bandwidth available across the map
        for(Instant instant : times) {
            Integer bandwidth = bwMap.get(instant);

            if (minimumBandwidth == null || bandwidth < minimumBandwidth) {
                minimumBandwidth = bandwidth;
                if(minimumBandwidth == 0) {
                    return connections;
                }
            }

            // Now we can check if we have enough bandwidth and time to perform the user's allocation
            Integer secondsBetween = (int) ((end.getTime() - Date.from(instant).getTime()) / 1000);
            Integer bandwidthNeeded = (int) Math.ceil(1.0 * volume / secondsBetween);

            if(bandwidthNeeded <= minimumBandwidth) {
                // If so, create the "best option" connection
                bestOptionBandwidth = minimumBandwidth;
                Integer durationSec = (int) (Math.ceil(1.0 * volume / minimumBandwidth));
                Date bestOptionStartTime = addTime(end, Calendar.SECOND, -1 * durationSec);

                // Create an initial connection from parameters
                Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                        spec.getDstPorts(), bestOptionBandwidth, bestOptionBandwidth, "endVolumeBest", bestOptionStartTime, end);

                testAndAddConnection(connections, conn);
                break;
            }
        }

        // If the loop did not find a single possible option, return the empty list of connections
        if(bestOptionBandwidth == null) return connections;

        // Sort the other options in descending order
        // This will allow us to check the highest possible bandwidth options first
        Collections.sort(otherOptions, Collections.reverseOrder());

        List<Integer> invalidOptions = new ArrayList<Integer>();

        for(Integer i = 0; i < otherOptions.size(); i++) {
            Integer option = otherOptions.get(i);
            Integer bandwidthNeeded = (int) (option / 100.0 * bestOptionBandwidth);
            Integer durationSec = (int) (Math.ceil(1.0 * volume / bandwidthNeeded));
            Date optionStart = addTime(end, Calendar.SECOND, -1 * durationSec);
            minimumBandwidth = null;

            for(Instant instant : times) {
                Integer bandwidth = bwMap.get(instant);

                if (minimumBandwidth == null || bandwidth < minimumBandwidth) {
                    minimumBandwidth = bandwidth;
                    if(minimumBandwidth == 0) {
                        return connections;
                    }
                }
                // Check if we have gone past the point where the current allocation will end
                if(optionStart.compareTo(Date.from(instant)) > 0) {
                    if(minimumBandwidth < bandwidthNeeded) {
                        invalidOptions.add(i);
                    }
                    else {
                        // This is a valid option!  Now attempt to create a connection
                        // Create an initial connection from parameters
                        Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                                spec.getDstPorts(), bandwidthNeeded, bandwidthNeeded, "endVolumeOption" + i, optionStart, end);

                        testAndAddConnection(connections, conn);
                    }
                    break;
                }
            }
        }

        // Now we must go through all invalid options and attempt to correct them

        for(Integer optionIndex : invalidOptions) {
            Integer option = otherOptions.get(optionIndex);
            Integer bandwidthNeeded = (int) (Math.ceil(option / 100.0 * bestOptionBandwidth));
            Integer durationSec = (int) (Math.ceil(1.0 * volume / bandwidthNeeded));
            Date optionStart = addTime(end, Calendar.SECOND, -1 * durationSec);
            minimumBandwidth = null;

            // Determine the "neighbor" bandwidth (the option immediately after the current one)
            Integer neighborBandwidth = null;
            if(optionIndex + 1 < otherOptions.size()) {
                neighborBandwidth = (int) (otherOptions.get(optionIndex + 1) / 100.0 * bestOptionBandwidth);
            }

            for(Instant instant : times) {
                Integer bandwidth = bwMap.get(instant);

                if (minimumBandwidth == null || bandwidth < minimumBandwidth) {
                    minimumBandwidth = bandwidth;
                    if(minimumBandwidth == 0) {
                        return connections;
                    }
                }
                // Check if we have gone past the point where the current allocation will end
                if(optionStart.compareTo(Date.from(instant)) > 0) {

                    if(minimumBandwidth < bandwidthNeeded) {
                        bandwidthNeeded = minimumBandwidth;
                        if(neighborBandwidth == null || bandwidthNeeded < neighborBandwidth) {
                            break; // This option is "dead"
                        }
                        // Recalculate our end time given the new bandwidth value
                        durationSec = (int) (Math.ceil(1.0 * volume / bandwidthNeeded));
                        optionStart = addTime(end, Calendar.SECOND, -1 * durationSec);
                    }
                    else {
                        // This option has been successfully reworked!  Now attempt to create a connection
                        // Create an initial connection from parameters
                        Connection conn = createInitialConnection(spec.getSrcDevice(), spec.getSrcPorts(), spec.getDstDevice(),
                                spec.getDstPorts(), bandwidthNeeded, bandwidthNeeded, "endVolumeOption(adjusted)" + optionIndex, optionStart, end);

                        testAndAddConnection(connections, conn);
                        break;
                    }
                }
            }
        }

        return connections;
    }

    /**
     * Generate a list of viable connections given a Start Date, requested transfer volume, and desired bandwidth.
     * Complete as early as possible.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithStartVolumeBandwidth(WhatifSpecification spec) {
        return new ArrayList<>();
    }

    /**
     * Generate a list of viable connections given an End Date, requested transfer volume, and desired bandwidth.
     * Complete transfer by deadline.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithEndVolumeBandwidth(WhatifSpecification spec) {
        return new ArrayList<>();
    }

    /**
     * Generate a list of viable connections given a requested transfer volume, and a transfer duration.
     * Find contiguous time periods to perform transfer.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithVolumeDuration(WhatifSpecification spec) {
        return new ArrayList<>();
    }

    /**
     * Generate a list of viable connections given just a requested transfer volume.
     * Complete transfer as early as possible.
     * @param spec - Submitted request specification.
     * @return A list of connections that could satisfy the demand.
     */
    public List<Connection> generateWithVolume(WhatifSpecification spec) {
        return new ArrayList<>();
    }

    public Connection createInitialConnection(String srcDevice, Set<String> srcPorts, String dstDevice, Set<String> dstPorts,
                                              Integer azMbps, Integer zaMbps, String connectionId, Date startDate,
                                              Date endDate){
        net.es.oscars.dto.spec.Specification spec = connectionGenerationService.generateSpecification(srcDevice, srcPorts, dstDevice, dstPorts,
                "any", "any", azMbps, zaMbps, new ArrayList<>(), new ArrayList<>(), new HashSet<>(),
                "PALINDROME", "NONE", 1, 1, 1, 1, connectionId,
                startDate, endDate);
        return connectionGenerationService.buildConnection(spec);
    }

    public BandwidthAvailabilityRequest createBwAvailRequest(String srcDevice, Set<String> srcPorts, String dstDevice,
                                                             Set<String> dstPorts, Integer minAzMbps, Integer minZaMbps,
                                                             Date startDate, Date endDate) {
        return bwAvailGenService.generateBandwidthAvailabilityRequest(srcDevice, srcPorts, dstDevice, dstPorts,
                new ArrayList<>(), new ArrayList<>(), minAzMbps, minZaMbps, 1, true, startDate, endDate);
    }


}
