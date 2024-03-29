package day02;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day02 {

    public static void main(String[] args) throws IOException {
        List<int[]> rounds = new ArrayList<>();

        Utils.readAllLines("inputs/input02.txt")
            .forEach(line -> rounds.add(new int[]{line.charAt(0), line.charAt(2)}));

        part1(rounds);
        part2(rounds);
    }

    static void part1(List<int[]> rounds) {
        var score = rounds.stream()
            .reduce(0, (acc, round) -> acc + getScore1(round), Integer::sum);

        System.out.println(score);
    }

    static void part2(List<int[]> rounds) {
        var score = rounds.stream()
            .reduce(0, (x, y) -> x + getScore2(y), Integer::sum);

        System.out.println(score);
    }

    private static int getScore1(int[] round) {
        int opponent = round[0] - 'A';
        int me = round[1] - 'X';
        int outcome = (me + (1 - opponent) + 3) % 3;

        return 3 * outcome + me + 1;
    }

    private static int getScore2(int[] round) {
        int opponent = round[0] - 'A';
        int outcome = round[1] - 'X';
        int me = (opponent + (outcome - 1) + 3) % 3;

        return 3 * outcome + me + 1;
    }
}
