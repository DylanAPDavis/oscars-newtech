package net.es.oscars.dto.topo.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Layer {
    ETHERNET("ETHERNET"),
    INTERNAL("INTERNAL"),
    MPLS("MPLS"),
    LOGICAL("LOGICAL");     // Added by Jeremy for Service-Layer Topology construction - Can be moved if necessary

    private String code;

    Layer(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, Layer> lookup = new HashMap<String, Layer>();

    static {
        for (Layer pc : EnumSet.allOf(Layer.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<Layer> get(String code) {
        Optional<Layer> result;
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }

}
