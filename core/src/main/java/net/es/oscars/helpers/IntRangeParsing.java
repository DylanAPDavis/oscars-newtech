package net.es.oscars.helpers;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.IntRange;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class IntRangeParsing {
    public static Boolean isValidIntRangeInput(String text) {
        Pattern re_valid = Pattern.compile(
                "# Validate comma separated integers/integer ranges.\n" +
                        "^             # Anchor to start of string.         \n" +
                        "[0-9]+        # Integer of 1st value (required).   \n" +
                        "(?:           # Range for 1st value (optional).    \n" +
                        "  :           # Colon separates range integer.      \n" +
                        "  [0-9]+      # Range integer of 1st value.        \n" +
                        ")?            # Range for 1st value (optional).    \n" +
                        "(?:           # Zero or more additional values.    \n" +
                        "  ,           # Comma separates additional values. \n" +
                        "  [0-9]+      # Integer of extra value (required). \n" +
                        "  (?:         # Range for extra value (optional).  \n" +
                        "    :         # Colon separates range integer.      \n" +
                        "    [0-9]+    # Range integer of extra value.      \n" +
                        "  )?          # Range for extra value (optional).  \n" +
                        ")*            # Zero or more additional values.    \n" +
                        "$             # Anchor to end of string.           ",
                Pattern.COMMENTS);
        Matcher m = re_valid.matcher(text);
        return m.matches();
    }

    public static List<IntRange> retrieveIntRanges(String text) throws NumberFormatException {


        List<IntRange> firstPass = new ArrayList<>();
        Pattern re_next_val = Pattern.compile(
                "# extract next integers/integer range value.    \n" +
                        "([0-9]+)      # $1: 1st integer (Base).         \n" +
                        "(?:           # Range for value (optional).     \n" +
                        "  :           # Colon separates range integer.   \n" +
                        "  ([0-9]+)    # $2: 2nd integer (Range)         \n" +
                        ")?            # Range for value (optional). \n" +
                        "(?:,|$)       # End on comma or string end.",
                Pattern.COMMENTS);
        Matcher m = re_next_val.matcher(text);
        while (m.find()) {
            Integer floor = Integer.parseInt(m.group(1));

            IntRange ir = IntRange.builder().floor(floor).ceiling(floor).build();
            if (m.group(2) != null) {
                Integer ceiling = Integer.parseInt(m.group(2));
                if (ceiling < floor) {
                    throw new NumberFormatException("ceiling < floor");
                }
                ir.setCeiling(ceiling);
            }
            firstPass.add(ir);
        }

        List<IntRange> result = new ArrayList<>();
        result.addAll(mergeIntRanges(firstPass));
        return result;

    }

    public static List<IntRange> mergeIntRanges(List<IntRange> input) {

        // don't mutate the list; copy it first
        List<IntRange> ranges = new ArrayList<>();
        input.stream().forEach(i -> {
            IntRange copy = IntRange.builder().floor(i.getFloor()).ceiling(i.getCeiling()).build();
            ranges.add(copy);
        });


        if (ranges == null || ranges.size() <= 1) {
            return ranges;
        }

        ranges.sort((a, b) -> a.getFloor().compareTo(b.getFloor()));

        List<IntRange> result = new ArrayList<>();

        IntRange prev = ranges.get(0);
        for (int i = 1; i < ranges.size(); i++) {
            IntRange curr = ranges.get(i);
            log.info("checking "+prev.toString() + " and "+curr.toString());

            // ceiling +1 can merge with floor; these are integers
            if (prev.getCeiling() +1  >= curr.getFloor()) {
                log.info("merging "+prev.toString() + " and "+curr.toString());

                Integer newCeiling = Math.max(prev.getCeiling(), curr.getCeiling());
                // merged case
                prev.setCeiling(newCeiling);
            } else {
                result.add(prev);
                prev = curr;
            }
        }

        result.add(prev);
        return result;


    }


}
