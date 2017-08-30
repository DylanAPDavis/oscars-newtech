package net.es.oscars.st.oper;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum OperState {
    ADMIN_DOWN_OPER_DOWN("ADMIN_DOWN_OPER_DOWN"),
    ADMIN_DOWN_OPER_UP("ADMIN_DOWN_OPER_UP"),
    ADMIN_UP_OPER_UP("ADMIN_UP_OPER_UP"),
    ADMIN_UP_OPER_DOWN("ADMIN_UP_OPER_DOWN");



    private String code;

    OperState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, OperState> lookup = new HashMap<String, OperState>();

    static {
        for (OperState pc : EnumSet.allOf(OperState.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<OperState> get(String code) {
        Optional<OperState> result;
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }
}
