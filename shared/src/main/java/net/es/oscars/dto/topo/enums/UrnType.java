package net.es.oscars.dto.topo.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum UrnType {
    DEVICE("DEVICE"),
    IFCE("IFCE");

    private String code;

    UrnType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, UrnType> lookup = new HashMap<String, UrnType>();

    static {
        for (UrnType pc : EnumSet.allOf(UrnType.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<UrnType> get(String code) {
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }


}
