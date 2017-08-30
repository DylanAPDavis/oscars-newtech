package net.es.oscars.pss.help;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.pss.cmd.CommandType;
import net.es.oscars.pss.beans.ConfigException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
@Slf4j
public class ParamsLoader {

    @Autowired
    private PssTestConfig pssTestConfig;

    public List<RouterTestSpec> loadSpecs(CommandType type) throws IOException,ConfigException {
        List<RouterTestSpec> result = new ArrayList<>();

        String[] extensions = {"json"};

        File dir = new File(pssTestConfig.getCaseDirectory());
        Iterator<File> files = FileUtils.iterateFiles(dir, extensions, false);
        ObjectMapper mapper = new ObjectMapper();
        String prefix = "";
        switch (type) {
            case BUILD:
                prefix = "build";
                break;
            case DISMANTLE:
                prefix = "dismantle";
                break;
            case OPERATIONAL_STATUS:
                prefix = "op_status";
                break;
            case CONFIG_STATUS:
                prefix = "cfg_status";
                break;
            case CONTROL_PLANE_STATUS:
            default:
                throw new ConfigException("no test specification for " + type);

        }

        while (files.hasNext()) {
            File f = files.next();
            if (f.getName().startsWith(prefix)) {
                log.info(f.getName() + " does start with "+prefix);
                log.info("loading spec from "+f.getName());
                RouterTestSpec spec = mapper.readValue(f, RouterTestSpec.class);
                spec.setFilename(f.getName());
                result.add(spec);
            }
        }
        return result;
    }

    public RouterTestSpec loadSpec(String path) throws IOException{
        ObjectMapper mapper = new ObjectMapper();

        File f = new File(path);
        RouterTestSpec spec = mapper.readValue(f, RouterTestSpec.class);
        spec.setFilename(path);
        return spec;
    }

}
