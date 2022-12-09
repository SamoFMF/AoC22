package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("inputs/input05.txt"));

        int numCrates = (lines.get(0).length() + 1) / 4;
        Stack[] stacks1 = new Stack[numCrates];
        Stack[] stacks2 = new Stack[numCrates];
        for (int i = 0; i < stacks1.length; i++) {
            stacks1[i] = new Stack();
            stacks2[i] = new Stack();
        }

        int iEmpty = 0;
        for (var line : lines) {
            iEmpty++;
            if (line.charAt(1) == '1') {
                break;
            }

            parseCratesLine(stacks1, stacks2, line);
        }
        iEmpty++;


        var moves = lines
            .stream()
            .skip(iEmpty)
            .map(Day05::parseMoveLine)
            .toArray(int[][]::new);

        part1(stacks1, moves);
        part2(stacks2, moves);
    }

    static void part1(Stack[] stacks, int[][] moves) {
        Arrays.stream(moves)
            .forEach(move -> parseMove01(stacks, move));

        Arrays.stream(stacks)
            .map(Stack::getLast)
            .reduce(String::concat)
            .ifPresent(System.out::println);
    }

    static void part2(Stack[] stacks, int[][] moves) {
        Arrays.stream(moves)
            .forEach(move -> parseMove02(stacks, move));

        Arrays.stream(stacks)
            .map(Stack::getLast)
            .reduce(String::concat)
            .ifPresent(System.out::println);
    }

    static void parseCratesLine(Stack[] stacks1, Stack[] stacks2, String line) {
        Matcher m = Pattern.compile("[A-Z]").matcher(line);

        while (m.find()) {
            var i = m.start() / 4;
            var crate = m.group();

            stacks1[i].addFirst(crate);
            stacks2[i].addFirst(crate);
        }
    }

    static int[] parseMoveLine(String line) {
        int[] moves = new int[3];

        Matcher m = Pattern.compile("\\d+").matcher(line);

        int i = 0;
        while (m.find()) {
            moves[i++] = Integer.parseInt(m.group());
        }

        return moves;
    }

    static void parseMove01(Stack[] stacks, int[] move) {
        moveCrates(move[0], stacks[move[1] - 1], stacks[move[2] - 1]);
    }

    static void parseMove02(Stack[] stacks, int[] move) {
        int quantity = move[0];
        Stack temp = new Stack();
        Stack from = stacks[move[1] - 1];
        Stack to = stacks[move[2] - 1];

        moveCrates(quantity, from, temp);
        moveCrates(quantity, temp, to);
    }

    static void moveCrates(int quantity, Stack from, Stack to) {
        for (int i = 0; i < quantity; i++) {
            moveCrate(from, to);
        }
    }

    static void moveCrate(Stack from, Stack to) {
        var crate = from.pop();
        to.add(crate);
    }
}
