package day11;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Monkey {
    private long numInspected;
    private long mod;
    private Deque<Long> items;

    private final Deque<Long> backupItems;
    private final Operation operation;
    private final long divisibleBy;
    private final int monkeyIfTrue;
    private final int monkeyIfFalse;

    public Monkey(List<String> lines) {
        numInspected = 0;

        items = new ArrayDeque<>();
        backupItems = new ArrayDeque<>();
        for (var item : lines.get(1).substring("\s\sStarting items:\s".length()).split(",\s")) {
            var itemVal = Long.parseLong(item);
            items.add(itemVal);
            backupItems.add(itemVal);
        }

        operation = new Operation(lines.get(2));

        divisibleBy = Long.parseLong(lines.get(3).split("\s")[5]);

        monkeyIfTrue = Integer.parseInt(lines.get(4).split("\s")[9]);
        monkeyIfFalse = Integer.parseInt(lines.get(5).split("\s")[9]);
    }

    public long getNumInspected() {
        return numInspected;
    }

    public Long getDivisibleBy() {
        return divisibleBy;
    }

    public void addMod(Long mod) {
        this.mod = mod;
    }

    public void reset() {
        items = backupItems;
        numInspected = 0;
    }

    public void doRound(Monkey[] monkeys, boolean divideBy3) {
        while (!items.isEmpty()) {
            var item = items.removeFirst();
            var worry = operation.calculate(item);

            if (divideBy3) {
                worry /= 3L;
            }

            worry %= mod;

            var monkey = monkeys[worry % divisibleBy == 0 ? monkeyIfTrue : monkeyIfFalse];
            throwItem(monkey, worry);
            numInspected++;
        }
    }

    public void throwItem(Monkey monkey, Long item) {
        monkey.catchItem(item);
    }

    public void catchItem(Long item) {
        items.addLast(item);
    }
}
