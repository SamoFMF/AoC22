package day04;

import java.util.Arrays;

public class Pair {
    private final Range[] pairs;

    public Pair(String line) {
        pairs = Arrays.stream(line
                .split(","))
            .map(Range::new)
            .toArray(Range[]::new);
    }

    public boolean isFullyContained() {
        return pairs[0].fullyContains(pairs[1]) || pairs[1].fullyContains(pairs[0]);
    }

    public boolean doesOverlap() {
        return pairs[0].overlapsOnRight(pairs[1]) || pairs[1].overlapsOnRight(pairs[0]);
    }
}
