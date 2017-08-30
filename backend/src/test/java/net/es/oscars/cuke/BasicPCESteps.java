package net.es.oscars.cuke;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import net.es.oscars.pce.PalindromicalPCE;
import net.es.oscars.resv.ent.EroHop;
import net.es.oscars.resv.ent.VlanJunction;
import net.es.oscars.resv.ent.VlanPipe;
import net.es.oscars.resv.enums.BwDirection;
import net.es.oscars.resv.enums.EroDirection;
import net.es.oscars.resv.svc.ResvLibrary;
import net.es.oscars.topo.beans.IntRange;
import net.es.oscars.topo.beans.TopoUrn;
import net.es.oscars.topo.svc.TopoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Transactional
public class BasicPCESteps extends CucumberSteps {
    @Autowired
    private CucumberWorld world;

    @Autowired
    private PalindromicalPCE palindromicalPCE;
    @Autowired
    private TopoService topoService;

    @When("^I ask for a path from \"([^\"]*)\" to \"([^\"]*)\" with az: (\\d+) and za: (\\d+)$")
    public void i_ask_for_a_path_from_to_with_az_and_za(String a, String z, int azBw, int zaBw) throws Throwable {

        VlanJunction aj = VlanJunction.builder()
                .refId(a)
                .deviceUrn(a)
                .build();
        VlanJunction zj = VlanJunction.builder()
                .refId(z)
                .deviceUrn(z)
                .build();

        VlanPipe vp = VlanPipe.builder()
                .a(aj)
                .z(zj)
                .azBandwidth(azBw)
                .zaBandwidth(zaBw).build();

        Map<String, Integer> availIngressBw;
        Map<String, Integer> availEgressBw;
        Map<String, Set<IntRange>> availVlans;
        Map<String, TopoUrn > baseline = topoService.getTopoUrnMap();


        availIngressBw = ResvLibrary.availableBandwidthMap(BwDirection.INGRESS, baseline, new HashMap<>());
        availEgressBw = ResvLibrary.availableBandwidthMap(BwDirection.EGRESS, baseline, new HashMap<>());
        availVlans = ResvLibrary.availableVlanMap(baseline, new HashSet<>());

        world.pipeEros = palindromicalPCE.palindromicERO(vp, availIngressBw, availEgressBw, availVlans);

    }

    @Then("^the resulting AZ ERO is:$")
    public void the_resulting_AZ_ERO_is(List<String> ero) throws Throwable {
        List<EroHop> hops = world.pipeEros.get(EroDirection.A_TO_Z);
        assert hops.size() == ero.size();
        for (int i = 0 ; i < ero.size() ; i++) {
            assert (hops.get(i).getUrn().equals(ero.get(i)));
        }

    }



}