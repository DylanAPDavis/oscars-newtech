package net.es.oscars.webui.cont;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.webui.ipc.TopologyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TopologyProvider topologyProvider;

    private final String oscarsUrl = "https://localhost:8000";

    @RequestMapping("/")
    public String home(Model model) {
        return "redirect:/react";
    }

    @RequestMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }


    @RequestMapping("/react/resv/list")
    public String reactListPage(Model model) {
        return "react";
    }

    @RequestMapping("/react/resv/gui")
    public String reactResvPage(Model model) {
        return "react";
    }

    @RequestMapping("/react/resv/whatif")
    public String reactWhatifPage(Model model) {
        return "react";
    }

    @RequestMapping("/react")
    public String reactPage(Model model) {
        return "redirect:/react/resv/list";
    }


    @RequestMapping(value = "/info/institutions", method = RequestMethod.GET)
    @ResponseBody
    public List<String> institution_suggestions() {
        log.info("giving suggestions");
        String restPath = oscarsUrl + "/users/institutions";

        String[] all_insts = restTemplate.getForObject(restPath, String[].class);
        List<String> institutions = new ArrayList<>();
        institutions.addAll(Arrays.asList(all_insts));
        return institutions;
    }


    @RequestMapping(value = "/info/vlanEdges", method = RequestMethod.GET)
    @ResponseBody
    public List<String> vlanEdge_suggestions() {
        log.info("giving vlanEdge suggestions");
        String restPath = oscarsUrl + "/topo/vlanEdges";

        String[] all_vlan_edges = restTemplate.getForObject(restPath, String[].class);
        List<String> vlan_edges = new ArrayList<>();
        vlan_edges.addAll(Arrays.asList(all_vlan_edges));
        return vlan_edges;
    }


    @RequestMapping(value = "/info/device/{device}/vlanEdges", method = RequestMethod.GET)
    @ResponseBody
    public List<String> vlanEdge_device_suggestions(@PathVariable("device") String device) {
        log.info("giving vlanEdge suggestions for device " + device);
        String restPath = oscarsUrl + "/topo/device/" + device + "/vlanEdges";

        String[] all_vlan_edges = restTemplate.getForObject(restPath, String[].class);
        List<String> vlan_edges = new ArrayList<>();
        vlan_edges.addAll(Arrays.asList(all_vlan_edges));
        Collections.sort(vlan_edges);
        return vlan_edges;
    }

    @RequestMapping(value = "/info/devices", method = RequestMethod.GET)
    @ResponseBody
    public List<String> device_suggestions() {
        log.info("giving device suggestions");
        String restPath = oscarsUrl + "/topo/devices";

        String[] all_devices = restTemplate.getForObject(restPath, String[].class);
        List<String> devices = new ArrayList<>();
        devices.addAll(Arrays.asList(all_devices));
        return devices;
    }

}