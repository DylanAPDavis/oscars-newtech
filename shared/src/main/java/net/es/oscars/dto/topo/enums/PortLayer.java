package net.es.oscars.dto.topo.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum PortLayer
{
    ETHERNET("ETHERNET"),
    MPLS("MPLS"),
    NONE("NONE");

    private String code;

    PortLayer(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    private static final Map<String, PortLayer> lookup = new HashMap<String, PortLayer>();

    static {
        for (PortLayer pc : EnumSet.allOf(PortLayer.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<PortLayer> get(String code) {
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }


}
