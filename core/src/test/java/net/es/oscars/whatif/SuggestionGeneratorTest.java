package net.es.oscars.whatif;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.AbstractCoreTest;
import net.es.oscars.bwavail.svc.BandwidthAvailabilityService;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityRequest;
import net.es.oscars.dto.bwavail.BandwidthAvailabilityResponse;
import net.es.oscars.dto.resv.Connection;
import net.es.oscars.dto.spec.ReservedBandwidth;
import net.es.oscars.dto.spec.ReservedEthPipe;
import net.es.oscars.dto.spec.ReservedMplsPipe;
import net.es.oscars.dto.spec.ReservedVlanJunction;
import net.es.oscars.pce.helpers.RepoEntityBuilder;
import net.es.oscars.pce.helpers.TopologyBuilder;
import net.es.oscars.resv.dao.ReservedBandwidthRepository;
import net.es.oscars.resv.svc.DateService;
import net.es.oscars.whatif.dto.WhatifSpecification;
import net.es.oscars.whatif.svc.SuggestionGenerator;
import net.es.oscars.whatif.svc.SuggestionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Slf4j
@Transactional
public class SuggestionGeneratorTest  extends AbstractCoreTest {

    @Autowired
    private SuggestionGenerator suggestionGenerator;

    @Autowired
    private BandwidthAvailabilityService bandwidthAvailabilityService;

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private DateService dateService;

    @Autowired
    private TopologyBuilder topologyBuilder;

    @Autowired
    private ReservedBandwidthRepository reservedBandwidthRepo;

    @Autowired
    private RepoEntityBuilder repoEntityBuilder;

    private void confirmBandwidths(List<Connection> suggestions, List<Integer> bws) {

        for(Integer i = 0; i < suggestions.size(); i++){
            Connection conn = suggestions.get(i);
            Integer bw = bws.get(i);
            Set<ReservedEthPipe> ethPipes = conn.getReserved().getVlanFlow().getEthPipes();
            Set<ReservedMplsPipe> mplsPipes = conn.getReserved().getVlanFlow().getMplsPipes();
            assert(mplsPipes.isEmpty());
            assert(ethPipes.size() == 2);

            for(ReservedEthPipe pipe : ethPipes){
                Set<ReservedBandwidth> reservedBandwidths = pipe.getReservedBandwidths();
                assert(reservedBandwidths.stream().allMatch(rsvBw -> rsvBw.getInBandwidth().equals(bw) && rsvBw.getEgBandwidth().equals(bw)));

                ReservedVlanJunction junctionA = pipe.getAJunction();
                ReservedVlanJunction junctionZ = pipe.getZJunction();
                assert(junctionA.getFixtures().stream().allMatch(fix -> fix.getReservedBandwidth().getInBandwidth().equals(bw)));
                assert(junctionZ.getFixtures().stream().allMatch(fix -> fix.getReservedBandwidth().getInBandwidth().equals(bw)));
            }
        }
    }

    private void reserveBandwidth(List<Integer> reservedBandwidths, List<String> reservedStartTimesStr, List<String> reservedEndTimesStr){

        List<String> reservedPortNames = new ArrayList<>();
        List<Integer> inBandwidths = new ArrayList<>();
        List<Integer> egBandwidths = new ArrayList<>();
        List<Instant> reservedStartTimes = new ArrayList<>();
        List<Instant> reservedEndTimes = new ArrayList<>();

        for(Integer i = 0; i < reservedBandwidths.size(); i++) {
            reservedPortNames.add("portA");

            Integer bandwidth = reservedBandwidths.get(i);
            inBandwidths.add(bandwidth);
            egBandwidths.add(bandwidth);

            reservedStartTimes.add(dateService.parseDate(reservedStartTimesStr.get(i)).toInstant());
            reservedEndTimes.add(dateService.parseDate(reservedEndTimesStr.get(i)).toInstant());
        }

        repoEntityBuilder.reserveBandwidth(reservedPortNames, reservedStartTimes, reservedEndTimes, inBandwidths, egBandwidths);
    }

    @Test
    public void startEndVolumeWithTwoConnectionsNoAllocations() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        String startDate = "01 01 2020 00:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();

        List<Connection> suggestions = suggestionGenerator.generateWithStartEndVolume(spec);

        assert(suggestions.size() == 2);

        confirmBandwidths(suggestions, Arrays.asList(2, 1000));
    }

    @Test
    public void startEndVolumeWithTwoConnections() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(600);
        reservedStartTimesStr.add("01 01 2020 05:00");
        reservedEndTimesStr.add("01 01 2020 10:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();

        List<Connection> suggestions = suggestionGenerator.generateWithStartEndVolume(spec);

        assert(suggestions.size() == 2);

        confirmBandwidths(suggestions, Arrays.asList(2, 400));
    }

    @Test
    public void startEndVolumeWithZeroConnections() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(900);
        reservedStartTimesStr.add("01 01 2020 00:03");
        reservedEndTimesStr.add("01 01 2020 00:05");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        String endDate = "01 01 2020 00:10";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();

        List<Connection> suggestions = suggestionGenerator.generateWithStartEndVolume(spec);

        assert(suggestions.size() == 0);
    }

    @Test
    public void startEndVolumeWithOneConnection(){
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(998);
        reservedStartTimesStr.add("01 01 2020 05:00");
        reservedEndTimesStr.add("01 01 2020 10:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();

        List<Connection> suggestions = suggestionGenerator.generateWithStartEndVolume(spec);

        assert(suggestions.size() == 1);

        confirmBandwidths(suggestions, Arrays.asList(2));
    }

    @Test
    public void startEndNoAllocations() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        String startDate = "01 01 2020 00:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();

        List<Connection> suggestions = suggestionGenerator.generateWithStartEnd(spec);

        assert(suggestions.size() == 1);

        confirmBandwidths(suggestions, Arrays.asList(1000));
    }

    @Test
    public void startEnd1Allocation() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(350);
        reservedStartTimesStr.add("01 01 2020 05:00");
        reservedEndTimesStr.add("01 01 2020 10:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();


        List<Connection> suggestions = suggestionGenerator.generateWithStartEnd(spec);

        assert(suggestions.size() == 1);

        confirmBandwidths(suggestions, Arrays.asList(650));
    }

    @Test
    public void startEndNonRelaventAllocation() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(100);
        reservedStartTimesStr.add("01 01 2020 02:00");
        reservedEndTimesStr.add("01 01 2020 04:59");
        reservedEgInBandwidths.add(555);
        reservedStartTimesStr.add("01 01 2020 07:00");
        reservedEndTimesStr.add("01 02 2020 07:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 05:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();

        List<Connection> suggestions = suggestionGenerator.generateWithStartEnd(spec);

        assert(suggestions.size() == 1);

        confirmBandwidths(suggestions, Arrays.asList(445));
    }

    @Test
    public void startEnd2Allocations() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(100);
        reservedStartTimesStr.add("01 01 2020 02:00");
        reservedEndTimesStr.add("01 01 2020 12:00");
        reservedEgInBandwidths.add(600);
        reservedStartTimesStr.add("01 01 2020 07:00");
        reservedEndTimesStr.add("01 01 2020 08:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();

        List<Connection> suggestions = suggestionGenerator.generateWithStartEnd(spec);

        assert(suggestions.size() == 1);

        confirmBandwidths(suggestions, Arrays.asList(300));
    }

    @Test
    public void startEndNoConnections() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(400);
        reservedStartTimesStr.add("01 01 2020 05:00");
        reservedEndTimesStr.add("01 01 2020 10:00");
        reservedEgInBandwidths.add(600);
        reservedStartTimesStr.add("01 01 2020 08:00");
        reservedEndTimesStr.add("01 01 2020 16:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        String endDate = "01 02 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .endDate(endDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();


        List<Connection> suggestions = suggestionGenerator.generateWithStartEnd(spec);

        assert(suggestions.size() == 0);
    }


    @Test
    public void startVolumeTest1() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(900);
        reservedStartTimesStr.add("01 01 2020 00:01");
        reservedEndTimesStr.add("01 01 2020 10:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 100000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();


        List<Connection> suggestions = suggestionGenerator.generateWithStartVolume(spec, Arrays.asList(25, 50, 75));

        assert(suggestions.size() == 4);

        confirmBandwidths(suggestions, Arrays.asList(100, 75, 50, 25));
    }

    @Test
    public void startVolumeTest2() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(900);
        reservedStartTimesStr.add("01 01 2020 00:01");
        reservedEndTimesStr.add("01 01 2020 04:00");

        reservedEgInBandwidths.add(35);
        reservedStartTimesStr.add("01 01 2020 03:00");
        reservedEndTimesStr.add("01 01 2020 04:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 1000000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();


        List<Connection> suggestions = suggestionGenerator.generateWithStartVolume(spec, Arrays.asList(25, 50, 75));

        assert(suggestions.size() == 4);

        confirmBandwidths(suggestions, Arrays.asList(100, 50, 25, 65));
    }

    @Test
    public void startVolumeTest3() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(900);
        reservedStartTimesStr.add("01 01 2020 00:01");
        reservedEndTimesStr.add("01 01 2020 14:00");

        reservedEgInBandwidths.add(80);
        reservedStartTimesStr.add("01 01 2020 06:00");
        reservedEndTimesStr.add("01 01 2020 10:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 1000000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();


        List<Connection> suggestions = suggestionGenerator.generateWithStartVolume(spec, Arrays.asList(25, 50, 75));

        assert(suggestions.size() == 3);

        confirmBandwidths(suggestions, Arrays.asList(100, 75, 50));
    }

    @Test
    public void startVolumeTest4() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(800);
        reservedStartTimesStr.add("01 01 2020 00:01");
        reservedEndTimesStr.add("01 01 2020 14:00");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 1000000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();


        List<Connection> suggestions = suggestionGenerator.generateWithStartVolume(spec, Arrays.asList(20, 40, 60, 80));

        assert(suggestions.size() == 5);

        confirmBandwidths(suggestions, Arrays.asList(200, 160, 120, 80, 40));
    }

    @Test
    public void startVolumeTest5() {
        reservedBandwidthRepo.deleteAll();
        topologyBuilder.buildTopo2();

        List<String> reservedStartTimesStr = new ArrayList<>();
        List<String> reservedEndTimesStr = new ArrayList<>();
        List<Integer> reservedEgInBandwidths = new ArrayList<>();

        reservedEgInBandwidths.add(800);
        reservedStartTimesStr.add("01 01 2020 00:01");
        reservedEndTimesStr.add("01 01 2020 14:00");

        reservedEgInBandwidths.add(90);
        reservedStartTimesStr.add("01 01 2020 02:00");
        reservedEndTimesStr.add("01 01 2020 02:30");

        reserveBandwidth(reservedEgInBandwidths, reservedStartTimesStr, reservedEndTimesStr);

        String startDate = "01 01 2020 00:00";
        Set<String> srcPorts = Collections.singleton("portA");
        String srcDevice = "nodeP";
        Set<String> dstPorts = Collections.singleton("portZ");
        String dstDevice = "nodeM";
        Integer volume = 1000000;
        Integer bandwidthMbps = null;
        Long durationMinutes = null;
        WhatifSpecification spec = WhatifSpecification.builder()
                .startDate(startDate)
                .srcPorts(srcPorts)
                .srcDevice(srcDevice)
                .dstPorts(dstPorts)
                .dstDevice(dstDevice)
                .volume(volume)
                .bandwidthMbps(bandwidthMbps)
                .durationMinutes(durationMinutes)
                .build();


        List<Connection> suggestions = suggestionGenerator.generateWithStartVolume(spec, Arrays.asList(20, 40, 60, 80));

        assert(suggestions.size() == 5);

        confirmBandwidths(suggestions, Arrays.asList(200, 160, 80, 40, 110));
    }

}
