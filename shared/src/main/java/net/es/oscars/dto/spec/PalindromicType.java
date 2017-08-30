package net.es.oscars.dto.spec;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum PalindromicType
{
    PALINDROME("PALINDROME"),
    NON_PALINDROME("NON_PALINDROME");

    private String code;

    PalindromicType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }


    private static final Map<String, PalindromicType> lookup = new HashMap<String, PalindromicType>();

    static {
        for (PalindromicType pc : EnumSet.allOf(PalindromicType.class)) {
            lookup.put(pc.getCode(), pc);
        }
    }

    public static Optional<PalindromicType> get(String code) {
        if (lookup.containsKey(code)) {
            return Optional.of(lookup.get(code));
        } else {
            return Optional.empty();
        }
    }
}
