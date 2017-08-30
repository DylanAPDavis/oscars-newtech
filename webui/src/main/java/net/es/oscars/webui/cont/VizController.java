package net.es.oscars.webui.cont;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.resv.Connection;
import net.es.oscars.dto.viz.VizGraph;
import net.es.oscars.webui.viz.VizExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Slf4j
@Controller
public class VizController {

    @Autowired
    private VizExporter vizExporter;
    @Autowired
    private RestTemplate restTemplate;

    private final String oscarsUrl = "https://localhost:8000";

    @RequestMapping(value = "/viz/topology/{classifier}", method = RequestMethod.GET)
    @ResponseBody
    public VizGraph viz_topology(@PathVariable String classifier) {
        if (classifier.equals("multilayer"))
        {
            return vizExporter.multilayerGraph();

        }
        else if(classifier.equals("unidirectional"))
        {
            return vizExporter.multilayerGraphUnidirectional();
        }
        else
        {
            throw new NoSuchElementException("bad classifier " + classifier);
        }
    }


    @RequestMapping(value = "/viz/connection/{connectionId}", method = RequestMethod.GET)
    @ResponseBody
    public VizGraph viz_connection(@PathVariable String connectionId) {
        String restPath = oscarsUrl + "/resv/get/" + connectionId;

        Connection conn = restTemplate.getForObject(restPath, Connection.class);


        return vizExporter.connection(conn);
    }

    @RequestMapping(value = "/viz/listPorts", method = RequestMethod.GET)
    @ResponseBody
    public String[] listTopoPorts()
    {
        Object[] portObjects = vizExporter.listTopologyPorts().toArray();
        String[] portStrings = Arrays.copyOf(portObjects, portObjects.length, String[].class);

        return portStrings;
    }
}