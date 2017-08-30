package net.es.oscars.dto.topo.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum IfceType {
    PORT("PORT");

    private String code;

    IfceType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, IfceType> lookup = new HashMap<String, IfceType>();

    static {
        for (IfceType pc : EnumSet.allOf(IfceType.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<IfceType> get(String code) {
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }


}
