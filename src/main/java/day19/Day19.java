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
                new int[]{0, 0, 0, 0},
                0
            )
        );

        var time = 0;
        var maxScore = 0;
        var maxGeodes = 0;
        Map<Resources, List<Resources>> visited = new HashMap<>();
        while (time < totalTime + 1) {
            time++;
            List<Node> next = new ArrayList<>();

            var newMaxGeodes = maxGeodes;
            for (var node : queue) {
                if (checkIfDominated(visited, node)) {
                    continue;
                }

                if (node.resources()[3] > maxScore) {
                    maxScore = node.resources()[3];
                }

                if (node.robots()[3] < maxGeodes - 1) continue;

                // Prioritize creating a new geode robot
                if (blueprint.canCreateRobot(3, node.resources())) {
                    var resources = addResourceRound(node.resources(), node.robots());
                    var robots = Arrays.copyOf(node.robots(), node.robots().length);
                    blueprint.addRobot(robots, resources, 3);
                    newMaxGeodes = Math.max(newMaxGeodes, robots[3]);
                    next.add(
                        new Node(
                            robots,
                            resources,
                            newMaxGeodes
                        )
                    );
                    continue;
                }

                // Try to create new robot
                for (int i = 0; i < 3; i++) {
                    if (blueprint.canCreateRobot(i, node.resources())) {
                        var resources = addResourceRound(node.resources(), node.robots());
                        var robots = Arrays.copyOf(node.robots(), node.robots().length);
                        blueprint.addRobot(robots, resources, i);
                        next.add(
                            new Node(
                                robots,
                                resources,
                                newMaxGeodes
                            )
                        );
                    }
                }

                // Continue without creating a new robot
                var resources = addResourceRound(node.resources(), node.robots());
                var robots = Arrays.copyOf(node.robots(), node.robots().length);
                next.add(
                    new Node(
                        robots,
                        resources,
                        newMaxGeodes
                    )
                );
            }

            queue = next;
            maxGeodes = newMaxGeodes;
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
}
