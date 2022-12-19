package day19;

import day19.models.*;
import utils.Utils;

import java.io.IOException;
import java.util.*;

public class Day19 {

    public static void main(String[] args) throws IOException {
        var blueprints = Utils.readFileStream("inputs/input19.txt")
            .map(Blueprint::new)
            .toList();

        part1(blueprints);
        part2(blueprints);
    }

    static void part1(List<Blueprint> blueprints) {
        var result = 0;
        for (int i = 0; i < blueprints.size(); i++) {
            result += (i + 1) * bfs(blueprints.get(i), 24);
        }
        System.out.println(result);
    }

    static void part2(List<Blueprint> blueprints) {
        var result = 1;
        for (int i = 0; i < 3; i++) {
            result *= bfs(blueprints.get(i), 32);
        }
        System.out.println(result);
    }

    static int bfs(Blueprint blueprint, int totalTime) {
        List<Node> queue = new ArrayList<>();
        queue.add(
            new Node(
                new int[]{1, 0, 0, 0},
                new int[]{0, 0, 0, 0}
            )
        );

        var time = 0;
        var maxScore = 0;
        Map<Resources, List<Resources>> visited = new HashMap<>();
        var costs = blueprint.getCosts();
        var maxOreCost = Arrays.stream(costs).mapToInt(c -> c[0]).max().orElse(0);
        while (time < totalTime + 1) {
            time++;
            List<Node> next = new ArrayList<>();

            for (var node : queue) {
                if (checkIfDominated(visited, node)) continue;

                if (node.resources()[3] > maxScore) {
                    maxScore = node.resources()[3];
                }

                // Prioritize creating a new geode robot
                if (blueprint.canCreateRobot(3, node.resources())) {
                    var resources = addResourceRound(node.resources(), node.robots());
                    var robots = Arrays.copyOf(node.robots(), node.robots().length);
                    blueprint.addRobot(robots, resources, 3);
                    next.add(
                        new Node(
                            robots,
                            resources
                        )
                    );
                } else {
                    var heuristics = getHeuristics(node.robots(), node.resources(), totalTime - time, blueprint.getGeodeCost());
                    if (heuristics <= maxScore) continue;

                    // Try to create a new robot
                    for (int i = 0; i < 3; i++) {
                        if (
                            (i == 0 && node.robots()[0] >= maxOreCost) // Producing more ore than can be spent
                                || (i == 1 && node.robots()[1] >= costs[2][1]) // Producing more clay than can be spent
                                || (i == 2 && node.robots()[2] >= costs[3][2]) // Producing more obsidian than can be spent
                                || !blueprint.canCreateRobot(i, node.resources()) // Not enough resources to build robot
                        ) {
                            continue;
                        }

                        var resources = addResourceRound(node.resources(), node.robots());
                        var robots = Arrays.copyOf(node.robots(), node.robots().length);
                        blueprint.addRobot(robots, resources, i);
                        next.add(
                            new Node(
                                robots,
                                resources
                            )
                        );
                    }

                    // Continue without creating a new robot
                    var resources = addResourceRound(node.resources(), node.robots());
                    var robots = Arrays.copyOf(node.robots(), node.robots().length);
                    next.add(
                        new Node(
                            robots,
                            resources
                        )
                    );
                }
            }

            queue = next;
        }

        return maxScore;
    }

    static int[] addResourceRound(int[] resources, int[] robots) {
        var newResources = new int[resources.length];
        for (int i = 0; i < resources.length; i++) {
            newResources[i] = resources[i] + robots[i];
        }
        return newResources;
    }

    static boolean checkIfDominated(Map<Resources, List<Resources>> visited, Node node) {
        var robot = new Resources(node.robots());
        var resources = new Resources(node.resources());
        if (visited.containsKey(robot)) {
            for (var existing : visited.get(robot)) {
                if (existing.dominates(resources)) {
                    return true;
                }
            }
        } else {
            visited.put(robot, new ArrayList<>());
        }
        visited.get(robot).add(resources);
        return false;
    }

    static int getHeuristics(int[] robot, int[] resources, int time, int[] geodeCost) {
        var prodOre = robot[0];
        var prodObs = robot[2];
        var prodGeo = robot[3];
        var ore = resources[0];
        var obs = resources[2];
        var geo = resources[3];

        for (int i = 0; i <= time; i++) {
            geo += prodGeo;
            if (ore >= geodeCost[0] && obs >= geodeCost[2]) {
                prodGeo++;
                ore -= geodeCost[0];
                obs -= geodeCost[2];
                ore += prodOre;
                obs += prodObs;
            } else if (prodOre / geodeCost[0] < prodObs / geodeCost[2]) {
                ore += prodOre;
                obs += prodObs;
                prodOre++;
            } else {
                ore += prodOre;
                obs += prodObs;
                prodObs++;
            }
        }
        return geo;
    }
}
