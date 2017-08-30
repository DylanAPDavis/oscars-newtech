package net.es.oscars.st.resv;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ResvState {
    IDLE_WAIT("IDLE_WAIT"),

    SUBMITTED("SUBMITTED"),
    HELD("HELD"),

    COMMITTING("COMMITTING"),

    ABORTING("ABORTING"),

    ABORT_FAILED("ABORT_FAILED");



    private String code;

    ResvState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, ResvState> lookup = new HashMap<String, ResvState>();

    static {
        for (ResvState pc : EnumSet.allOf(ResvState.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<ResvState> get(String code) {
        Optional<ResvState> result;
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }
}

