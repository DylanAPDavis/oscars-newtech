package net.es.oscars.dto.topo.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum DeviceType {
    ROUTER("ROUTER"),
    SWITCH("SWITCH");

    private String code;

    DeviceType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, DeviceType> lookup = new HashMap<String, DeviceType>();

    static {
        for (DeviceType pc : EnumSet.allOf(DeviceType.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<DeviceType> get(String code) {
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }


}
