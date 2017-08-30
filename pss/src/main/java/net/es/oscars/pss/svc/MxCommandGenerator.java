package net.es.oscars.pss.svc;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.pss.params.mx.MxParams;
import net.es.oscars.pss.beans.ConfigException;
import net.es.oscars.pss.beans.MxTemplatePaths;
import net.es.oscars.pss.tpl.Assembler;
import net.es.oscars.pss.tpl.Stringifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class MxCommandGenerator {

    @Autowired
    private Stringifier stringifier;

    @Autowired
    private Assembler assembler;

    public String dismantle(MxParams params) throws ConfigException {
        this.protectVsNulls(params);
        this.verifyPaths(params);

        MxTemplatePaths mtp = MxTemplatePaths.builder()
                .lsp("mx/dismantle-mx-mpls-lsp.ftl")
                .qos("mx/dismantle-mx-qos.ftl")
                .sdp("mx/dismantle-mx-sdp.ftl")
                .ifces("mx/dismantle-mx-ifces.ftl")
                .path("mx/dismantle-mx-mpls-path.ftl")
                .vpls("mx/dismantle-mx-vpls-service.ftl")
                .build();
        return fill(mtp, params);
    }

    public String build(MxParams params) throws ConfigException {
        this.protectVsNulls(params);
        this.verifyPaths(params);

        MxTemplatePaths mtp = MxTemplatePaths.builder()
                .lsp("mx/build-mx-mpls-lsp.ftl")
                .qos("mx/build-mx-qos.ftl")
                .sdp("mx/build-mx-sdp.ftl")
                .ifces("mx/build-mx-ifces.ftl")
                .path("mx/build-mx-mpls-path.ftl")
                .vpls("mx/build-mx-vpls-service.ftl")
                .build();
        return fill(mtp, params);
    }

    private String fill(MxTemplatePaths tp, MxParams params) throws ConfigException {

        String top = "mx/mx-top.ftl";

        Map<String, Object> root;
        List<String> fragments = new ArrayList<>();
        try {
            if (params.getPaths().isEmpty()) {
                log.info("Empty paths, skipping..");
            } else {
                root = new HashMap<>();
                root.put("paths", params.getPaths());
                String pathConfig = stringifier.stringify(root, tp.getPath());
                fragments.add(pathConfig);
            }

            if (params.getLsps().isEmpty()) {
                log.info("Empty LSPs, skipping..");
            } else {
                root = new HashMap<>();
                root.put("lsps", params.getLsps());
                String lspConfig = stringifier.stringify(root, tp.getLsp());
                fragments.add(lspConfig);
            }

            root = new HashMap<>();
            root.put("vpls", params.getMxVpls());
            String vplsServiceConfig = stringifier.stringify(root, tp.getVpls());
            fragments.add(vplsServiceConfig);


            return assembler.assemble(fragments, top);
        } catch (IOException | TemplateException ex) {
            log.error("templating error", ex);
            throw new ConfigException("template system error");
        }
    }


    private void protectVsNulls(MxParams params) throws ConfigException {

        if (params == null) {
            log.error("whoa whoa whoa there, no passing null params!");
            throw new ConfigException("null Juniper MX params");
        }
        if (params.getMxVpls() == null) {
            throw new ConfigException("null Juniper MX VPLS");
        }
        if (params.getMxVpls().getIfces() == null) {
            throw new ConfigException("null Juniper MX VPLS ifce list");
        }
        if (params.getApplyQos() == null) {
            params.setApplyQos(false);
        }
        if (params.getLoopbackAddress() == null) {
            throw new ConfigException("null Juniper MX loopback address");
        }
        if (params.getLoopbackInterface() == null ) {
            throw new ConfigException("null Juniper MX loopback ifce");
        }
        if (params.getPolicing() == null) {
            params.setPolicing(new HashMap<>());
        }
        if (params.getPaths() == null) {
            params.setPaths(new ArrayList<>());
        }
        if (params.getLsps() == null) {
            params.setLsps(new HashMap<>());
        }

    }

    private void verifyPaths(MxParams params) throws ConfigException {



    }



}
