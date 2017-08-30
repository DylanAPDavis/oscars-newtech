package net.es.oscars.webui.cont;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.cfg.StartupConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Controller
public class AdminConfController {

    @Autowired
    private RestTemplate restTemplate;

    private final String oscarsUrl = "https://localhost:8000";

    @RequestMapping(value = "/admin/comp_list", method = RequestMethod.GET)
    public String admin_comp_list(Model model) {

        String restPath = oscarsUrl + "/configs/all";
        String[] components = restTemplate.getForObject(restPath, String[].class);

        model.addAttribute("components", components);
        return "admin_comp_list";
    }

    @RequestMapping(value = "/admin/comp_edit/{comp_name}", method = RequestMethod.GET)
    public String admin_comp_edit(@PathVariable String comp_name, Model model) throws IOException {

        String restPath = oscarsUrl + "/configs/get/" + comp_name;
        String uglyJson = restTemplate.getForObject(restPath, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(uglyJson, Object.class);
        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);


        // log.info("got config: " + prettyJson);

        StartupConfig component = new StartupConfig();
        component.setName(comp_name);
        component.setConfigJson(prettyJson);

        model.addAttribute("component", component);
        return "admin_comp_edit";
    }


    @RequestMapping(value = "/admin/comp_update_submit", method = RequestMethod.POST)
    public String admin_comp_update_submit(@ModelAttribute StartupConfig updatedConfig) {
        String name = updatedConfig.getName();
        log.info("updating " + name);


        String restPath = oscarsUrl + "/configs/update";
        restTemplate.postForObject(restPath, updatedConfig, StartupConfig.class);

        return "redirect:/admin/comp_edit/" + name;
    }

}