package day23;

import utils.Utils;

import java.io.IOException;

public class Day23 {

    public static void main(String[] args) throws IOException {
        var solver = new Solver();
        Utils.readAllLines("inputs/input23.txt")
            .forEach(solver::addRow);

        part1(solver);
        part2(solver);
    }

    static void part1(Solver solver) {
        for (int i = 0; i < 10; i++) solver.playRound();

        System.out.println(getNumEmpty(solver));
    }

    static void part2(Solver solver) {
        int i = 11;
        while (solver.playRound()) i++;

        System.out.println(i);
    }

    static int getNumEmpty(Solver solver) {
        var extremes = solver.getExtremes();
        return (extremes[1] + 1 - extremes[0]) * (extremes[3] + 1 - extremes[2]) - solver.getGridSize();
    }
}
