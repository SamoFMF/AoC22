package day05;

import utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class Day05 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input05.txt");

        int numCrates = (lines.get(0).length() + 1) / 4;
        Stack[] stacks1 = new Stack[numCrates];
        Stack[] stacks2 = new Stack[numCrates];
        for (int i = 0; i < stacks1.length; i++) {
            stacks1[i] = new Stack();
            stacks2[i] = new Stack();
        }

        int iEmpty = 0;
        while (lines.get(iEmpty).charAt(1) != '1') {
            iEmpty++;
        }

        for (int i = iEmpty - 1; i >= 0; i--) {
            parseCratesLine(stacks1, stacks2, lines.get(i));
        }

        iEmpty += 2;


        var moves = new int[lines.size() - iEmpty][];
        for (int i = 0; i < moves.length; i++) {
            moves[i] = parseMoveLine(lines.get(i + iEmpty));
        }

        part1(stacks1, moves);
        part2(stacks2, moves);
    }

    static void part1(Stack[] stacks, int[][] moves) {
        Arrays.stream(moves)
            .forEach(move -> parseMove1(stacks, move));

        printResult(stacks);
    }

    static void part2(Stack[] stacks, int[][] moves) {
        Arrays.stream(moves)
            .forEach(move -> parseMove2(stacks, move));

        printResult(stacks);
    }

    static void printResult(Stack[] stacks) {
        var toPrint = new StringBuilder();
        for (var stack : stacks) {
            toPrint.append(stack.getLast());
        }
        System.out.println(toPrint);
    }

    static void parseCratesLine(Stack[] stacks1, Stack[] stacks2, String line) {
        for (int i = 0; i < stacks1.length; i++) {
            var c = line.charAt(4 * i + 1);
            if (c != ' ') {
                stacks1[i].add(c);
                stacks2[i].add(c);
            }
        }
    }

    static int[] parseMoveLine(String line) {
        var data = line.split("\s");
        return new int[]{Integer.parseInt(data[1]), Integer.parseInt(data[3]), Integer.parseInt(data[5])};
    }

    static void parseMove1(Stack[] stacks, int[] move) {
        int quantity = move[0];
        Stack from = stacks[move[1] - 1];
        Stack to = stacks[move[2] - 1];

        for (int i = 0; i < quantity; i++) {
            to.add(from.pop());
        }
    }

    static void parseMove2(Stack[] stacks, int[] move) {
        int quantity = move[0];
        Stack from = stacks[move[1] - 1];
        Stack to = stacks[move[2] - 1];

        to.addAll(from.removeLastItems(quantity));
    }
}
