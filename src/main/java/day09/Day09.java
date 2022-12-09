package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day09 {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("inputs/input09.txt"));

        part1(lines);
        part2(lines);
    }

    static void part1(List<String> lines) {
        var grid = new Grid(2);
        lines.forEach(grid::handleLine);

        System.out.println(grid.getNumVisitedPositions());
    }

    static void part2(List<String> lines) {
        var grid = new Grid(10);
        lines.forEach(grid::handleLine);

        System.out.println(grid.getNumVisitedPositions());
    }
}
