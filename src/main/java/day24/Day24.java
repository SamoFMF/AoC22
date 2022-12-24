package day24;

import utils.Position;
import utils.Utils;

import java.io.IOException;
import java.util.stream.IntStream;

public class Day24 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input24.txt");

        var start = IntStream
            .range(0, lines.get(0).length())
            .filter(i -> lines.get(0).charAt(i) == '.')
            .findFirst()
            .orElse(0);

        var end = IntStream
            .range(0, lines.get(0).length())
            .filter(i -> lines.get(lines.size() - 1).charAt(i) == '.')
            .findFirst()
            .orElse(0);

        var solver = new Solver(
            new Position(start, 0),
            new Position(end, lines.size() - 1),
            lines.get(0).length() - 1,
            lines.size() - 1
        );
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                var pos = new Position(j, i);
                switch (line.charAt(j)) {
                    case '>' -> solver.addTornado(pos, new Position(1, 0));
                    case 'v' -> solver.addTornado(pos, new Position(0, 1));
                    case '<' -> solver.addTornado(pos, new Position(-1, 0));
                    case '^' -> solver.addTornado(pos, new Position(0, -1));
                    case '#' -> solver.addWallBlock(pos);
                    default -> {
                    }
                }
            }
        }
        solver.addWallBlock(new Position(start, -1));
        solver.addWallBlock(new Position(end, lines.size()));

        var steps = part1(solver);
        part2(solver, steps);
    }

    static int part1(Solver solver) {
        var steps = solver.solve(0, false);
        System.out.println(steps);
        return steps;
    }

    static void part2(Solver solver, int steps) {
        steps = solver.solve(steps, true);
        steps = solver.solve(steps, false);
        System.out.println(steps);
    }
}
