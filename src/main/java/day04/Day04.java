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

        part1(pairs);
        part2(pairs);
    }

    static void part1(Pair[] pairs) {
        var numFullyContained = Arrays.stream(pairs)
            .filter(Pair::isFullyContained)
            .count();

        System.out.println(numFullyContained);
    }

    static void part2(Pair[] pairs) {
        var numOverlap = Arrays.stream(pairs)
            .filter(Pair::doesOverlap)
            .count();

        System.out.println(numOverlap);
    }
}
