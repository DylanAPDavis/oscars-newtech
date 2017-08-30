package net.es.oscars.st.prov;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ProvState {


    INITIAL("INITIAL"),

    READY("READY"),

    GENERATED("GENERATED"),

    DISMANTLED("DISMANTLED"),

    BUILDING("BUILDING"),

    BUILT("BUILT"),

    DISMANTLING("DISMANTLING"),

    FAILED("FAILED");

    private String code;

    ProvState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, ProvState> lookup = new HashMap<String, ProvState>();

    static {
        for (ProvState pc : EnumSet.allOf(ProvState.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<ProvState> get(String code) {
        Optional<ProvState> result;
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }
}
