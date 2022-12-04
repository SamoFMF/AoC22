package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day04 {

    public static void main(String[] args) throws IOException {
        var pairs = Files.readAllLines(Paths.get("inputs/input04.txt"))
            .stream()
            .map(Pair::new)
            .toArray(Pair[]::new);

        part01(pairs);
        part02(pairs);
    }

    static void part01(Pair[] pairs) {
        var numFullyContained = Arrays.stream(pairs)
            .filter(Pair::isFullyContained)
            .count();

        System.out.println(numFullyContained);
    }

    static void part02(Pair[] pairs) {
        var numOverlap = Arrays.stream(pairs)
            .filter(Pair::doesOverlap)
            .count();

        System.out.println(numOverlap);
    }
}
