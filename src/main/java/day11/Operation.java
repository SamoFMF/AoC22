package day11;

import java.util.function.BiFunction;

public class Operation {
    private final Long left;
    private final Long right;
    private final BiFunction<Long, Long, Long> op;

    public Operation(String line) {
        var data = line.split(" ");

        if ("old".equals(data[5])) {
            left = null;
        } else {
            left = Long.parseLong(data[5]);
        }

        if ("old".equals(data[7])) {
            right = null;
        } else {
            right = Long.parseLong(data[7]);
        }

        if ("*".equals(data[6])) {
            op = (x, y) -> x * y;
        } else {
            op = Long::sum;
        }
    }

    public long calculate(long old) {
        return op.apply(left == null ? old : left, right == null ? old : right);
    }
}
