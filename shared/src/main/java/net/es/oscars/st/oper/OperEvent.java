package net.es.oscars.st.oper;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum OperEvent {

    ADMIN_UP("ADMIN_UP"),
    ADMIN_DOWN("ADMIN_DOWN"),

    OPER_UP("OPER_UP"),
    OPER_DOWN("OPER_DOWN");


    private String code;

    OperEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, OperEvent> lookup = new HashMap<String, OperEvent>();

    static {
        for (OperEvent pc : EnumSet.allOf(OperEvent.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<OperEvent> get(String code) {
        Optional<OperEvent> result;
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }
}
