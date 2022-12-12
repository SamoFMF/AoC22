package day12;

import utils.Utils;

import java.io.IOException;

public class Day12 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input12.txt");

        var heightmap = new Heightmap(lines);

        part1(heightmap);
        part2(heightmap);
    }

    static void part1(Heightmap heightmap) {
        heightmap.setFactor(x -> ('z' - Double.valueOf(x)) / ('z' - 'a'));
        heightmap.setComparator((height, curHeight) -> height - curHeight <= 1);

        var start = heightmap.getStart();
        var end = heightmap.getEnd();
        System.out.println(heightmap.findShortestPath(
                start,
                node -> end.equals(node.position())
            )
        );
    }

    static void part2(Heightmap heightmap) {
        heightmap.setFactor(x -> (Double.valueOf(x) - 'a') / ('z' - 'a'));
        heightmap.setComparator((height, curHeight) -> curHeight - height <= 1);

        var end = heightmap.getEnd();
        System.out.println(heightmap.findShortestPath(
                end,
                node -> heightmap.getAtPosition(node.position()) == 'a'
            )
        );
    }
}
