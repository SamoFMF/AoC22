package day16;

import day16.models.Valve;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day16 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input16.txt");

        Map<String, Valve> valves = new HashMap<>();
        for (var line : lines) {
            var valve = new Valve(line);
            valves.put(valve.getId(), valve);
        }

        for (var valve : valves.values()) {
            valve.calculateDistances(valves);
        }

        var solver = new Solver(valves);

        part1(solver);
        part2(solver);
    }

    static void part1(Solver solver) {
        System.out.println(solver.solve("AA", 30, 1));
    }

    static void part2(Solver solver) {
        System.out.println(solver.solve("AA", 26, 2));
    }
}
