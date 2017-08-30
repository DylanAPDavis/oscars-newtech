package net.es.oscars.dto.topo.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum VertexType {
    NETWORK("NETWORK"),
    PORT("PORT"),
    ROUTER("ROUTER"),
    SWITCH("SWITCH"),
    VIRTUAL("VIRTUAL");     // Added by Jeremy for Service-Layer Topology construction - Can be moved if necessary


    private String code;

    VertexType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    private static final Map<String, VertexType> lookup = new HashMap<String, VertexType>();

    static {
        for (VertexType pc : EnumSet.allOf(VertexType.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<VertexType> get(String code) {
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }


}
