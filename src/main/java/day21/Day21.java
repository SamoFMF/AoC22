package day21;

import utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day21 {

    public static void main(String[] args) throws IOException {
        Map<String, Monkey> monkeys = new HashMap<>();
        Utils.readFileStream("inputs/input21.txt")
            .map(line -> line.split(" "))
            .map(Monkey::new)
            .forEach(monkey -> monkeys.put(monkey.getName(), monkey));

        part1(monkeys);
        part2(monkeys);
    }

    static void part1(Map<String, Monkey> monkeys) {
        System.out.println(monkeys.get("root").calculateValue(monkeys));
    }

    static void part2(Map<String, Monkey> monkeys) {
        var equals = Arrays.stream(monkeys
                .get("root")
                .getEquation()
                .getValues())
            .map(monkeys::get)
            .toArray(Monkey[]::new);

        var root = new Monkey(new String[]{"root:", equals[0].getName(), "-", equals[1].getName()});
        monkeys.put("root", root);
        long low = 0L;
        long high = 10000000000000L;
        while (true) {
            long mid = (low + high) / 2;
            monkeys.values().forEach(Monkey::resetValue);
            monkeys.put("humn", new Monkey(new String[]{"humn:", String.valueOf(mid)}));
            var diff = root.calculateValue(monkeys);
            if (diff > 0) {
                low = mid + 1;
            } else if (diff < 0) {
                high = mid;
            } else {
                System.out.println(mid);
                return;
            }
        }
    }
}
