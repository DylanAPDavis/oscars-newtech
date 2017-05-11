package net.es.oscars.whatif.svc;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.bwavail.svc.BandwidthAvailabilityGenerationService;
import net.es.oscars.bwavail.svc.BandwidthAvailabilityService;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityRequest;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityResponse;
import net.es.oscars.dto.resv.Connection;
import net.es.oscars.resv.svc.DateService;
import net.es.oscars.whatif.dto.WhatifSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class SuggestionService {

    DateService dateService;

    SuggestionGenerator suggestionGenerator;

    @Autowired
    public SuggestionService(DateService dateService, SuggestionGenerator suggestionGenerator) {
        this.dateService = dateService;
        this.suggestionGenerator = suggestionGenerator;
    }


    /**
     * Generate a list of suggested Connections given a WhatifSpecification.
     * @param spec - WhatifSpecification containing the source, destination, start date, end date, and data volume.
     * @return A list of viable connection options.
     */
    public List<Connection> generateSuggestions(WhatifSpecification spec) {
        return buildConnectionList(spec);
    }

    private List<Connection> buildConnectionList(WhatifSpecification spec){
        List<Connection> connections = new ArrayList<>();
        String startDate = spec.getStartDate();
        String endDate = spec.getEndDate();
        Integer requestedBandwidth = spec.getBandwidthMbps();
        Integer requestedVolume = spec.getVolume();
        Long duration = spec.getDurationMinutes();

        boolean specifiedStartEndVolume = startDate != null && endDate != null && requestedVolume != null;
        boolean specifiedStartEnd = startDate != null && endDate != null;
        boolean specifiedStartVolume = startDate != null && requestedVolume != null;
        boolean specifiedEndVolume = endDate != null && requestedVolume != null;
        boolean specifiedStartVolumeBandwidth = startDate != null && requestedVolume != null && requestedBandwidth != null;
        boolean specifiedEndVolumeBandwidth = endDate != null && requestedVolume != null && requestedBandwidth != null;
        boolean specifiedVolumeDuration = requestedVolume != null && duration != null;
        boolean specifiedVolume = requestedVolume != null;

        // Specified start & end & volume -> find the right bandwidth
        if(specifiedStartEndVolume){
            connections.addAll(suggestionGenerator.generateWithStartEndVolume(spec));
        }
        if(connections.size() == 0 && specifiedStartEnd){
            connections.addAll(suggestionGenerator.generateWithStartEnd(spec));
        }
        if(connections.size() == 0 && specifiedStartVolume){
//            connections.addAll(suggestionGenerator.generateWithStartVolume(spec));
        }
        if(connections.size() == 0 && specifiedEndVolume){
            connections.addAll(suggestionGenerator.generateWithEndVolume(spec));
        }
        if(connections.size() == 0 && specifiedStartVolumeBandwidth){
            connections.addAll(suggestionGenerator.generateWithStartVolumeBandwidth(spec));
        }
        if(connections.size() == 0 && specifiedEndVolumeBandwidth){
            connections.addAll(suggestionGenerator.generateWithEndVolumeBandwidth(spec));
        }
        if(connections.size() == 0 && specifiedVolumeDuration){
            connections.addAll(suggestionGenerator.generateWithVolumeDuration(spec));
        }
        if(connections.size() == 0 && specifiedVolume){
            connections.addAll(suggestionGenerator.generateWithVolume(spec));
        }

        return connections;
    }



}
