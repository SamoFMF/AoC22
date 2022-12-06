package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day01 {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("inputs/input01.txt"));

        List<Integer> calories = new ArrayList<>();
        calories.add(0);

        for (var line : lines) {
            if (line.isEmpty()) {
                calories.add(0);
            } else {
                var prev = calories.get(calories.size() - 1);
                calories.set(calories.size() - 1, prev + Integer.parseInt(line));
            }
        }

        var sorted = calories
            .stream()
            .sorted()
            .skip(calories.size() - 3)
            .toList();

        part1(sorted);
        part2(sorted);
    }

    private static void part1(List<Integer> calories) {
        System.out.println(calories.get(2));
    }

    private static void part2(List<Integer> calories) {
        calories
            .stream()
            .reduce(Integer::sum)
            .ifPresent(System.out::println);
    }
}
