package day21;

import java.util.Map;
import java.util.function.BiFunction;

public class Monkey {
    private final String name;
    private final Equation equation;
    private final Long originalValue;

    private Long value;

    public Monkey(String[] data) {
        name = data[0].substring(0, data[0].length() - 1);
        if (data.length == 2) {
            originalValue = Long.parseLong(data[1]);
            value = originalValue;
            equation = null;
        } else {
            originalValue = null;
            value = null;
            var monkeys = new String[]{data[1], data[3]};
            BiFunction<Long, Long, Long> op = switch (data[2]) {
                case "+" -> Long::sum;
                case "-" -> (x, y) -> x - y;
                case "*" -> (x, y) -> x * y;
                default -> (x, y) -> x / y;
            };
            equation = new Equation(monkeys, op);
        }
    }

    public String getName() {
        return name;
    }

    public void resetValue() {
        value = originalValue;
        if (equation != null) {
            equation.resetValue();
        }
    }

    public Equation getEquation() {
        return equation;
    }

    public Long calculateValue(Map<String, Monkey> monkeys) {
        if (value == null) {
            value = equation.solve(monkeys);
        }

        return value;
    }
}
