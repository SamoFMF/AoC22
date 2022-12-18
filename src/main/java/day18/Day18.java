package day18;

import utils.Utils;

import java.io.IOException;

public class Day18 {

    public static void main(String[] args) throws IOException {
        var cubes = new Cubes();
        for (var line : Utils.readAllLines("inputs/input18.txt")) {
            cubes.addCube(new Position(line.split(",")));
        }

        part1(cubes);
        part2(cubes);
    }

    static void part1(Cubes cubes) {
        System.out.println(cubes.getSurfaceArea());
    }

    static void part2(Cubes cubes) {
        System.out.println(cubes.getSurfaceArea() - cubes.findPockets());
    }
}
