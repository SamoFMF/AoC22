package day21;

import java.util.Map;
import java.util.function.BiFunction;

public class Equation {
    private final String[] values;
    private final BiFunction<Long, Long, Long> operation;

    private Long value;

    public Equation(String[] values, BiFunction<Long, Long, Long> operation) {
        this.values = values;
        this.operation = operation;
        value = null;
    }

    public Long solve(Map<String, Monkey> monkeys) {
        if (value == null) {
            var left = monkeys.get(values[0]);
            var right = monkeys.get(values[1]);

            value = operation.apply(left.calculateValue(monkeys), right.calculateValue(monkeys));
        }

        return value;
    }

    public String[] getValues() {
        return values;
    }

    public void resetValue() {
        value = null;
    }
}
