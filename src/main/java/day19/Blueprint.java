package day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Blueprint {

    private final int[][] costs;

    public Blueprint(String line) {
        costs = new int[4][4];

        Matcher matcher = Pattern.compile("Each ([a-z]+) robot costs (\\d+ [a-z]+)( and \\d+ [a-z]+)?( and \\d+ [a-z]+)?.")
            .matcher(line);
        while (matcher.find()) {
            var robot = costs[resourceToIndex(matcher.group(1))];
            for (int i = 2; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) == null) break;
                var values = matcher.group(i).split("\s");
                var idx = resourceToIndex(values[values.length - 1]);
                robot[idx] = Integer.parseInt(values[values.length - 2]);
            }
        }
    }

    public int[][] getCosts() {
        return costs;
    }

    public int[] getGeodeCost() {
        return costs[3];
    }

    public void addRobot(int[] robots, int[] resources, int robotIdx) {
        robots[robotIdx]++;
        spendResources(resources, robotIdx);
    }

    public void spendResources(int[] resources, int robotIdx) {
        for (int i = 0; i < 4; i++) {
            resources[i] -= costs[robotIdx][i];
            assert resources[i] >= 0;
        }
    }

    public boolean canCreateRobot(int robotIdx, int[] resources) {
        for (int i = 0; i < resources.length; i++) {
            if (resources[i] < costs[robotIdx][i]) {
                return false;
            }
        }
        return true;
    }

    private int resourceToIndex(String resource) {
        return switch (resource) {
            case "ore" -> 0;
            case "clay" -> 1;
            case "obsidian" -> 2;
            case "geode" -> 3;
            default -> throw new RuntimeException("Unknown resource " + resource);
        };
    }
}
