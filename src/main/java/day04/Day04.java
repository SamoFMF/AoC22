package day04;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Day04 {

    public static void main(String[] args) throws IOException {
        List<Pair> pairs = new ArrayList<>();

        Utils.readAllLines("inputs/input04.txt")
            .forEach(line -> pairs.add(new Pair(line)));

        part1(pairs);
        part2(pairs);
    }

    static void part1(List<Pair> pairs) {
        System.out.println(count(pairs, Pair::isFullyContained));
    }

    static void part2(List<Pair> pairs) {
        System.out.println(count(pairs, Pair::doesOverlap));
    }

    static int count(List<Pair> pairs, Function<Pair, Boolean> requirement) {
        int num = 0;
        for (var pair : pairs) {
            if (requirement.apply(pair)) {
                num++;
            }
        }
        return num;
    }
}
