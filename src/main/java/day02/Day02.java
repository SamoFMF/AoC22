package day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    public static void main(String[] args) throws IOException {
        var rounds = Files.readAllLines(Paths.get("inputs/input02.txt"))
            .stream()
            .map(line -> line.split(" "))
            .map(x -> Arrays.stream(x)
                .mapToInt(y -> y.charAt(0))
                .toArray())
            .toList();

        part01(rounds);
        part02(rounds);
    }

    static void part01(List<int[]> rounds) {
        var score = rounds.stream()
            .reduce(0, (acc, round) -> acc + getScore01(round), Integer::sum);

        System.out.println(score);
    }

    static void part02(List<int[]> rounds) {
        var score = rounds.stream()
            .reduce(0, (x, y) -> x + getScore02(y), Integer::sum);

        System.out.println(score);
    }

    private static int getScore01(int[] round) {
        int opponent = round[0] - 'A';
        int me = round[1] - 'X';
        int outcome = (me + (1 - opponent) + 3) % 3;

        return 3 * outcome + me + 1;
    }

    private static int getScore02(int[] round) {
        int opponent = round[0] - 'A';
        int outcome = round[1] - 'X';
        int me = (opponent + (outcome - 1) + 3) % 3;

        return 3 * outcome + me + 1;
    }
}
