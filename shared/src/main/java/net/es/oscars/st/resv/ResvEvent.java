package net.es.oscars.st.resv;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ResvEvent {

    UPSTREAM_SUBMIT_RECEIVED("UPSTREAM_SUBMIT_RECEIVED"),

    UPSTREAM_ABORT_RECEIVED("UPSTREAM_ABORT_RECEIVED"),

    UPSTREAM_COMMIT_RECEIVED("UPSTREAM_COMMIT_RECEIVED"),
    UPSTREAM_COMMIT_TIMEOUT("UPSTREAM_COMMIT_TIMEOUT"),

    LOCAL_AND_DOWNSTREAM_CHECK_PASS("LOCAL_AND_DOWNSTREAM_CHECK_PASS"),
    ANY_CHECK_FAIL("ANY_CHECK_FAIL"),

    LOCAL_AND_DOWNSTREAM_COMMIT_PASS("LOCAL_AND_DOWNSTREAM_COMMIT_PASS"),
    ANY_COMMIT_FAIL("ANY_COMMIT_FAIL"),

    LOCAL_AND_DOWNSTREAM_ABORT_PASS("LOCAL_AND_DOWNSTREAM_ABORT_PASS"),
    ANY_ABORT_FAIL("ANY_ABORT_FAIL");



    private String code;

    ResvEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, ResvEvent> lookup = new HashMap<String, ResvEvent>();

    static {
        for (ResvEvent pc : EnumSet.allOf(ResvEvent.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<ResvEvent> get(String code) {
        Optional<ResvEvent> result;
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }
}
