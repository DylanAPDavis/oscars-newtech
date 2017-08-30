package net.es.oscars.topo.rest;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.rsrc.ReservableBandwidth;
import net.es.oscars.dto.spec.ReservedBandwidth;
import net.es.oscars.dto.topo.DevicePortMap;
import net.es.oscars.dto.topo.ReservedBandwidths;
import net.es.oscars.dto.topo.Topology;
import net.es.oscars.dto.topo.enums.Layer;
import net.es.oscars.resv.ent.ReservedBandwidthE;
import net.es.oscars.topo.ent.ReservableBandwidthE;
import net.es.oscars.topo.svc.TopoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
public class TopoController {
    private TopoService topoService;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TopoController(TopoService topoService) {
        this.topoService = topoService;
    }


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException(NoSuchElementException ex) {
        log.warn(ex.getMessage(), ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn(ex.getMessage(), ex);
    }


    @RequestMapping(value = "/topo/vlanEdges", method = RequestMethod.GET)
    @ResponseBody
    public List<String> vlanEdges() {
        log.info("getting vlan edges");
        return topoService.edges(Layer.ETHERNET);
    }


    @RequestMapping(value = "/topo/device/{device}/vlanEdges", method = RequestMethod.GET)
    @ResponseBody
    public List<String> deviceVlanEdges(@PathVariable("device") String device) {
        log.info("getting device ETHERNET edges");
        return topoService.edgesWithCapability(device, Layer.ETHERNET);
    }


    @RequestMapping(value = "/topo/devices", method = RequestMethod.GET)
    @ResponseBody
    public List<String> devices() {
        log.info("getting devices");
        return topoService.devices();
    }

    @RequestMapping(value = "/topo/all", method = RequestMethod.GET)
    @ResponseBody
    public Topology topology() {
        log.info("Getting entire topology");
        return topoService.getMultilayerTopology();
    }


    @RequestMapping(value = "/topo/device_port_map", method = RequestMethod.GET)
    @ResponseBody
    public DevicePortMap devicePortMap() {
        log.info("getting devicePortMap");
        return topoService.buildDeviceToPortMap();
    }

    @RequestMapping(value = "/topo/multilayer", method = RequestMethod.GET)
    @ResponseBody
    public Topology topo_layer() {
        log.info("getting multilayer topo");
        return topoService.getMultilayerTopology();
    }

    @RequestMapping(value = "/topo/allport/bwcapacity", method = RequestMethod.GET)
    @ResponseBody
    public List<ReservableBandwidth> portCapacity() {
        List<ReservableBandwidthE> portCapacity = topoService.reservableBandwidths();
        List<ReservableBandwidth> portCapDTO = new ArrayList<>();

        for (ReservableBandwidthE oneCap : portCapacity) {
            ReservableBandwidth oneDTO = ReservableBandwidth.builder()
                    .ingressBw(oneCap.getIngressBw())
                    .egressBw(oneCap.getEgressBw())
                    .topoVertexUrn(oneCap.getUrn().getUrn())
                    .build();

            portCapDTO.add(oneDTO);
        }

        return portCapDTO;
    }

    @RequestMapping(value = "/topo/reservedbw", method = RequestMethod.POST)
    @ResponseBody
    public ReservedBandwidths reservedBandwidth(@RequestBody List<String> resUrns) {
        ReservedBandwidths rbws = this.getAllReservedBandwidth();
        rbws.getBandwidths().removeIf(bw -> !resUrns.contains(bw.getContainerConnectionId()));
        return rbws;
    }

    @RequestMapping(value = "/topo/allreservedbw", method = RequestMethod.GET)
    @ResponseBody
    public ReservedBandwidths getAllReservedBandwidth() {
        List<ReservedBandwidthE> allResBwE = topoService.reservedBandwidths();

        List<ReservedBandwidth> allResBwDTO = new ArrayList<>();

        for (ReservedBandwidthE oneBwE : allResBwE) {
            ReservedBandwidth oneBwDTO = new ReservedBandwidth();
            modelMapper.map(oneBwE, oneBwDTO);

            allResBwDTO.add(oneBwDTO);
        }
        return ReservedBandwidths.builder().bandwidths(allResBwDTO).build();
    }
}