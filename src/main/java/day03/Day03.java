package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class Day03 {

    public static void main(String[] args) throws IOException {
        var rucksacks = Files.readAllLines(Paths.get("inputs/input03.txt"))
            .stream()
            .map(Rucksack::new)
            .toList();

        part01(rucksacks);
        part02(rucksacks);
    }

    static void part01(List<Rucksack> rucksacks) {
        rucksacks
            .stream()
            .map(Rucksack::getRepeated)
            .map(Day03::getValueOfChar)
            .reduce(Integer::sum)
            .ifPresent(System.out::println);
    }

    static void part02(List<Rucksack> rucksacks) {
        var sum = IntStream.range(0, rucksacks.size() / 3)
            .map(i -> 3 * i)
            .map(i -> rucksacks.get(i)
                .getCommon(
                    rucksacks.get(i + 1).getEverything(),
                    rucksacks.get(i + 2).getEverything()
                )
            )
            .map(Day03::getValueOfChar)
            .sum();

        System.out.println(sum);
    }

    static int getValueOfChar(Integer val) {
        if ('a' <= val && val <= 'z') {
            return val + 1 - 'a';
        } else {
            return val + 27 - 'A';
        }
    }
}
