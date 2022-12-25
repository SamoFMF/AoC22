package day25;

import day25.models.DigitSum;

public class Snafu {
    public static String add(String a, String b) {
        if (a.length() < b.length()) {
            return add(b, a);
        }

        var a0 = a.length() - 1;
        var b0 = b.length() - 1;
        var builder = new StringBuilder();
        var digitSum = new DigitSum('0', '0');
        for (int i = 0; i <= b0; i++) {
            digitSum = addDigits(digitSum.carry(), a.charAt(a0 - i));
            var carry = digitSum.carry();
            digitSum = addDigits(digitSum.digit(), b.charAt(b0 - i));
            builder.append(digitSum.digit());
            digitSum = new DigitSum('0', addDigits(digitSum.carry(), carry).digit());
        }

        for (int i = b0 + 1; i <= a0; i++) {
            digitSum = addDigits(digitSum.carry(), a.charAt(a0 - i));
            builder.append(digitSum.digit());
        }

        if (digitSum.carry() != '0') {
            builder.append(digitSum.carry());
        }

        return builder.reverse().toString();
    }

    private static DigitSum addDigits(char a, char b) {
        var x = digitToDecimal(a) + digitToDecimal(b);
        char carry;
        if (x >= 3) {
            carry = '1';
            x -= 5;
        } else if (x <= -3) {
            carry = '-';
            x += 5;
        } else {
            carry = '0';
        }

        return new DigitSum(
            decimalToDigit(x),
            carry
        );
    }

    private static long digitToDecimal(char c) {
        return switch (c) {
            case '2' -> 2;
            case '1' -> 1;
            case '0' -> 0;
            case '-' -> -1;
            default -> -2;
        };
    }

    private static char decimalToDigit(long x) {
        return switch ((int) x) {
            case -1 -> '-';
            case -2 -> '=';
            default -> String.valueOf(x).charAt(0);
        };
    }
}
