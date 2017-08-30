package net.es.oscars.resv;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.CoreUnitTestConfiguration;
import net.es.oscars.dto.spec.PalindromicType;
import net.es.oscars.dto.spec.SurvivabilityType;
import net.es.oscars.helpers.RequestedEntityBuilder;
import net.es.oscars.pce.exc.PCEException;
import net.es.oscars.pce.helpers.TopologyBuilder;
import net.es.oscars.pss.PSSException;
import net.es.oscars.resv.dao.ReservedBandwidthRepository;
import net.es.oscars.resv.ent.ConnectionE;
import net.es.oscars.resv.ent.RequestedBlueprintE;
import net.es.oscars.resv.ent.RequestedVlanPipeE;
import net.es.oscars.resv.ent.ReservedBandwidthE;
import net.es.oscars.resv.svc.ResvService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=CoreUnitTestConfiguration.class)
@Transactional
public class AbortTest {

    @Autowired
    private ResvService resvService;

    @Autowired
    private TopologyBuilder topologyBuilder;

    @Autowired
    private RequestedEntityBuilder testBuilder;

    @Autowired
    private ReservedBandwidthRepository reservedBandwidthRepository;

    @Test
    public void abortTest(){
        topologyBuilder.buildTopo8();

        RequestedBlueprintE requestedBlueprint;
        Set<RequestedVlanPipeE> reqPipes = new HashSet<>();
        ConnectionE conn;

        Date startDate = new Date(Instant.now().plus(15L, ChronoUnit.MINUTES).getEpochSecond());
        Date endDate = new Date(Instant.now().plus(1L, ChronoUnit.DAYS).getEpochSecond());

        String srcDevice = "nodeP";
        String dstDevice = "nodeQ";
        List<String> srcPorts = Stream.of("portA").collect(Collectors.toList());
        List<String> dstPorts = Stream.of("portZ").collect(Collectors.toList());
        Integer azBW = 25;
        Integer zaBW = 25;
        String vlan = "any";
        PalindromicType palindrome = PalindromicType.PALINDROME;
        SurvivabilityType survivability = SurvivabilityType.SURVIVABILITY_NONE;

        RequestedVlanPipeE pipeAZ = testBuilder.buildRequestedPipe(srcPorts, srcDevice, dstPorts, dstDevice, azBW, zaBW, palindrome, survivability, vlan, 1, 1);
        reqPipes.add(pipeAZ);

        requestedBlueprint = testBuilder.buildRequest(reqPipes, 1, 1, "reusedBlueprint");

        conn = testBuilder.buildConnection(requestedBlueprint, testBuilder.buildSchedule(startDate, endDate), "conn1", "The Connection");

        // Reserve the connection
        try {
            resvService.hold(conn);
        }
        catch (PCEException | PSSException pceE)
        {
            log.error("", pceE);
        }

        resvService.abort(conn);

        List<ReservedBandwidthE> resvBandwidths = reservedBandwidthRepository.findAll();
        log.info(resvBandwidths.toString());
    }
}
