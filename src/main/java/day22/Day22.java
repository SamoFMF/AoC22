package day22;

import utils.Position;
import utils.Utils;

import java.io.IOException;
import java.util.regex.Pattern;

public class Day22 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input22.txt");
        var grid = new Grid();
        for (int i = 0; i < lines.size() - 2; i++) {
            grid.addRow(lines.get(i));
        }

        part1(grid, lines.get(lines.size() - 1));
        part2(grid, lines.get(lines.size() - 1));
    }

    static void part1(Grid grid, String instructions) {
        System.out.println(solve(grid, instructions, true));
    }

    static void part2(Grid grid, String instructions) {
        var N = 50;
        var cube = new CubeLayer[6];
        for (int i = 0; i < 6; i++) {
            cube[i] = new CubeLayer(N);
        }
        for (int i = 0; i < N; i++) {
            var splitRows = grid.getGrid().get(i).splitInHalf();
            cube[0].addRow(splitRows[0], i);
            cube[1].addRow(splitRows[1], i);
            cube[2].addRow(grid.getGrid().get(i + N), i);
            splitRows = grid.getGrid().get(i + 2 * N).splitInHalf();
            cube[3].addRow(splitRows[0], i);
            cube[4].addRow(splitRows[1], i);
            cube[5].addRow(grid.getGrid().get(i + 3 * N), i);
        }

        // Hard code cube assembly
        cube[0].setRightFunction(pos -> new UpdateMe(pos, new Position(1, 0)));
        cube[0].setDownFunction(pos -> new UpdateMe(pos, new Position(0, 1)));
        cube[0].setLeftFunction(pos -> new UpdateMe(
            new Position(0, 3 * N - 1 - pos.y()),
            new Position(1, 0)
        ));
        cube[0].setUpFunction(pos -> new UpdateMe(
            new Position(0, 2 * N + pos.x()),
            new Position(1, 0)
        ));

        cube[1].setRightFunction(pos -> new UpdateMe(
            new Position(2 * N - 1, 3 * N - 1 - pos.y()),
            new Position(-1, 0)
        ));
        cube[1].setDownFunction(pos -> new UpdateMe(
            new Position(2 * N - 1, pos.x() - N),
            new Position(-1, 0)
        ));
        cube[1].setLeftFunction(pos -> new UpdateMe(pos, new Position(-1, 0)));
        cube[1].setUpFunction(pos -> new UpdateMe(
            new Position(pos.x() - 2 * N, 4 * N - 1),
            new Position(0, -1)
        ));

        cube[2].setRightFunction(pos -> new UpdateMe(
            new Position(pos.y() + N, N - 1),
            new Position(0, -1)
        ));
        cube[2].setDownFunction(pos -> new UpdateMe(pos, new Position(0, 1)));
        cube[2].setLeftFunction(pos -> new UpdateMe(
            new Position(pos.y() - N, 2 * N),
            new Position(0, 1)
        ));
        cube[2].setUpFunction(pos -> new UpdateMe(pos, new Position(0, -1)));

        cube[3].setRightFunction(pos -> new UpdateMe(pos, new Position(1, 0)));
        cube[3].setDownFunction(pos -> new UpdateMe(pos, new Position(0, 1)));
        cube[3].setLeftFunction(pos -> new UpdateMe(
            new Position(N, 3 * N - 1 - pos.y()),
            new Position(1, 0)
        ));
        cube[3].setUpFunction(pos -> new UpdateMe(
            new Position(N, pos.x() + N),
            new Position(1, 0)
        ));

        cube[4].setRightFunction(pos -> new UpdateMe(
            new Position(3 * N - 1, 3 * N - 1 - pos.y()),
            new Position(-1, 0)
        ));
        cube[4].setDownFunction(pos -> new UpdateMe(
            new Position(N - 1, 2 * N + pos.x()),
            new Position(-1, 0)
        ));
        cube[4].setLeftFunction(pos -> new UpdateMe(pos, new Position(-1, 0)));
        cube[4].setUpFunction(pos -> new UpdateMe(pos, new Position(0, -1)));

        cube[5].setRightFunction(pos -> new UpdateMe(
            new Position(pos.y() - 2 * N, 3 * N - 1),
            new Position(0, -1)
        ));
        cube[5].setDownFunction(pos -> new UpdateMe(
            new Position(pos.x() + 2 * N, 0),
            new Position(0, 1)
        ));
        cube[5].setLeftFunction(pos -> new UpdateMe(
            new Position(pos.y() - 2 * N, 0),
            new Position(0, 1)
        ));
        cube[5].setUpFunction(pos -> new UpdateMe(pos, new Position(0, -1)));

        for (var layer : cube) {
            grid.addCubeLayer(layer);
        }

        System.out.println(solve(grid, instructions, false));
    }

    static long solve(Grid grid, String instructions, boolean isPart1) {
        grid.moveToStart();

        var matcher = Pattern.compile("(\\d+)|R|L").matcher(instructions);
        while (matcher.find()) {
            grid.move(matcher.group(), isPart1);
        }

        return grid.getScore();
    }
}
