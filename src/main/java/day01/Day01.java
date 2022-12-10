package day01;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {

    public static void main(String[] args) throws IOException {
        List<Integer> calories = new ArrayList<>();
        int curCalories = 0;

        for (var line : Utils.readAllLines("inputs/input01.txt")) {
            if (line.isEmpty()) {
                calories.add(curCalories);
                curCalories = 0;
            } else {
                curCalories += Integer.parseInt(line);
            }
        }

        calories.sort(Collections.reverseOrder());

        part1(calories);
        part2(calories);
    }

    private static void part1(List<Integer> calories) {
        System.out.println(calories.get(0));
    }

    private static void part2(List<Integer> calories) {
        System.out.println(calories.get(0) + calories.get(1) + calories.get(2));
    }
}
