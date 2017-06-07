package net.es.oscars.pss.svc;


import net.es.oscars.dto.pss.cmd.Command;
import net.es.oscars.dto.topo.enums.DeviceModel;
import net.es.oscars.pss.beans.ConfigException;
import net.es.oscars.pss.prop.RancidProps;
import net.es.oscars.pss.rancid.RancidArguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouterConfigBuilder {
    private RancidProps props;
    private AluCommandGenerator acg;
    private MxCommandGenerator mcg;
    private ExCommandGenerator ecg;

    @Autowired
    public RouterConfigBuilder(RancidProps props,
                               AluCommandGenerator acg,
                               MxCommandGenerator mcg,
                               ExCommandGenerator ecg) {
        this.props = props;
        this.acg = acg;
        this.mcg = mcg;
        this.ecg = ecg;
    }

    public String generate(Command command) throws ConfigException {
        String result = "";
        switch (command.getType()) {
            case CONFIG_STATUS:
                break;
            case OPERATIONAL_STATUS:
                break;
            case CONTROL_PLANE_STATUS:
                result = controlPlaneCheck(command.getDevice(), command.getModel()).getRouterConfig();
                break;
            case BUILD:
                result = build(command).getRouterConfig();
                break;
            case DISMANTLE:
                result = dismantle(command).getRouterConfig();
                break;
        }
        return result;
    }


    public RancidArguments controlPlaneCheck(String device, DeviceModel model) throws ConfigException {
        String routerConfig;
        switch (model) {
            case ALCATEL_SR7750:
                routerConfig = "echo \"OSCARS PSS control plane check\"";
                break;
            case JUNIPER_MX:
            case JUNIPER_EX:
                routerConfig = "show chassis hardware";
                break;
            default:
                throw new ConfigException("unknown model");
        }

        return buildRouterConfig(routerConfig, device, model);
    }


    public RancidArguments build(Command command) throws ConfigException {
        String routerConfig = "";
        DeviceModel model = command.getModel();
        switch (model) {
            case ALCATEL_SR7750:
                routerConfig = acg.build(command.getAlu());
                break;
            case JUNIPER_EX:
                routerConfig = ecg.build(command.getEx());
                break;
            case JUNIPER_MX:
                routerConfig = mcg.build(command.getMx());
                break;
            default:
                throw new ConfigException("unknown model");
        }

        return buildRouterConfig(routerConfig, command.getDevice(), command.getModel());
    }

    public RancidArguments dismantle(Command command) throws ConfigException {
        String routerConfig = "";
        DeviceModel model = command.getModel();
        switch (model) {
            case ALCATEL_SR7750:
                routerConfig = acg.dismantle(command.getAlu());
                break;
            case JUNIPER_EX:
                routerConfig = ecg.dismantle(command.getEx());
                break;
            case JUNIPER_MX:
                routerConfig = mcg.dismantle(command.getMx());
                break;
            default:
                throw new ConfigException("unknown model");
        }

        return buildRouterConfig(routerConfig, command.getDevice(), command.getModel());
    }

    public RancidArguments buildRouterConfig(String routerConfig, String device, DeviceModel model) throws ConfigException {
        String execPath;
        String cloginrc = props.getCloginrc();
        String dir = props.getDir();

        switch (model) {
            case ALCATEL_SR7750:
                execPath = dir + "/alulogin";
                break;
            case JUNIPER_MX:
            case JUNIPER_EX:
                execPath = dir + "/jlogin";
                break;
            default:
                throw new ConfigException("unknown model");
        }

        return RancidArguments.builder()
                .cloginrc(cloginrc)
                .executable(execPath)
                .routerConfig(routerConfig)
                .router(device)
                .build();

    }

}
