package net.es.oscars.ui.rest;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.viz.DevicePositions;
import net.es.oscars.ui.pop.UIPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UIController {
    @Autowired
    public UIController(UIPopulator populator) {
        this.populator = populator;
    }

    private UIPopulator populator;

    @RequestMapping(value = "/ui/positions", method = RequestMethod.GET)
    @ResponseBody
    public DevicePositions ui_positions() {
        log.info("getting ui default positions");
        return populator.getPositions();
    }

}