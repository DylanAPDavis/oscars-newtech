package net.es.oscars.dto.spec;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum SurvivabilityType
{
    SURVIVABILITY_NONE("NONE"),
    SURVIVABILITY_PARTIAL("PARTIAL"),       // Current implementation: MPLS-Segment survivability
    SURVIVABILITY_TOTAL("TOTAL");           // Current implementation: End-to-End Path survivability

    private String code;

    SurvivabilityType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, SurvivabilityType> lookup = new HashMap<String, SurvivabilityType>();

    static {
        for (SurvivabilityType pc : EnumSet.allOf(SurvivabilityType.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<SurvivabilityType> get(String code) {
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }
}
