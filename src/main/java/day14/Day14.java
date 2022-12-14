package day14;

import utils.Utils;

import java.io.IOException;

public class Day14 {

    public static void main(String[] args) throws IOException {
        var cave = new Cave();
        Utils.readAllLines("inputs/input14.txt")
            .forEach(cave::processLine);

        part1(cave);
        part2(cave);
    }

    static void part1(Cave cave) {
        System.out.println(cave.doSimulation1());
    }

    static void part2(Cave cave) {
        System.out.println(cave.doSimulation2());
    }
}
