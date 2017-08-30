package net.es.oscars.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntRange {
    private Integer floor;
    private Integer ceiling;
    public boolean contains(Integer i) {
        return (floor <= i && ceiling >= i);
    }


    public static Set<IntRange> subtract(IntRange range, Integer i) throws NoSuchElementException {
        HashSet<IntRange> result = new HashSet<>();
        if (!range.contains(i)) {
            throw new NoSuchElementException("range "+range.toString()+" does not contain "+i);
        }
        // remove last one: return an empty set
        if (range.getFloor().equals(range.getCeiling())) {
            return result;
        }

        // remove ceiling or floor: return a single range
        if (range.getCeiling().equals(i)) {
            IntRange r = IntRange.builder().ceiling(i-1).floor(range.getFloor()).build();
            result.add(r);
        } else if (range.getFloor().equals(i)) {
            IntRange r = IntRange.builder().ceiling(range.getCeiling()).floor(i+1).build();
            result.add(r);
        } else {
            // split into two
            IntRange top = IntRange.builder().floor(range.getFloor()).ceiling(i-1).build();
            IntRange bottom = IntRange.builder().floor(i+1).ceiling(range.getCeiling()).build();
            result.add(top);
            result.add(bottom);
        }
        return result;
    }


}
