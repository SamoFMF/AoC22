package day11;

import utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class Day11 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input11.txt");

        var monkeys = new Monkey[(lines.size() + 1) / 7];
        var mod = 1L;
        for (int i = 0; i < monkeys.length; i++) {
            monkeys[i] = new Monkey(lines.subList(7 * i, 7 * i + 6));
            mod *= monkeys[i].getDivisibleBy();
        }

        for (var monkey : monkeys) {
            monkey.addMod(mod);
        }

        part1(monkeys);

        for (var monkey : monkeys) {
            monkey.reset();
        }

        part2(monkeys);
    }

    static void part1(Monkey[] monkeys) {
        doRounds(monkeys, 20, true);
        printResult(monkeys);
    }

    static void part2(Monkey[] monkeys) {
        doRounds(monkeys, 10_000, false);
        printResult(monkeys);
    }

    static void doRounds(Monkey[] monkeys, int numRounds, boolean divideBy3) {
        for (int i = 0; i < numRounds; i++) {
            for (var monkey : monkeys) {
                monkey.doRound(monkeys, divideBy3);
            }
        }
    }

    static void printResult(Monkey[] monkeys) {
        var result = Arrays.stream(monkeys)
            .mapToLong(Monkey::getNumInspected)
            .sorted()
            .skip(monkeys.length - 2)
            .reduce(1L, (acc, val) -> acc * val);

        System.out.println(result);
    }
}
