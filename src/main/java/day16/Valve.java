package day16;

import java.util.*;

public class Valve {
    private final String id;
    private final int rate;
    private final String[] neighbours;
    private final Map<Valve, Integer> distances;
    private final List<Integer> openedAt;

    public Valve(String line) {
        var data = line.split(";\stunnels?\sleads?\sto\svalves?\s");
        id = data[0].substring("Valve\s".length(), "Valve\s".length() + 2);
        rate = Integer.parseInt(data[0].substring("Valve\sAA\shas\sflow\srate=".length()));
        neighbours = data[1].split(",\s");

        distances = new HashMap<>();
        openedAt = new ArrayList<>();
        openedAt.add(0);
    }

    public int getOpenedAt() {
        return openedAt.get(openedAt.size() - 1);
    }

    public int openValve(int time) {
        var prev = openedAt.get(openedAt.size() - 1);
        openedAt.add(time);
        return time - prev;
    }

    public void closeValve() {
        openedAt.remove(openedAt.size() - 1);
    }

    public String getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public String[] getNeighbours() {
        return neighbours;
    }

    public Map<Valve, Integer> getDistances() {
        return distances;
    }

    public void calculateDistances(Map<String, Valve> valves) {
        List<Valve> toAdd = new ArrayList<>();
        for (String s : neighbours) {
            toAdd.add(valves.get(s));
        }

        var d = 1;
        Set<Valve> visited = new HashSet<>();

        while (toAdd.size() > 0 && distances.size() < valves.size()) {
            List<Valve> next = new ArrayList<>();
            for (var valve : toAdd) {
                if (valve.getRate() > 0) {
                    distances.put(valve, d);
                }
                visited.add(valve);
                for (var neighbour : valve.getNeighbours()) {
                    var nValve = valves.get(neighbour);
                    if (!visited.contains(nValve)) {
                        next.add(nValve);
                    }
                }
            }
            toAdd = next;
            d++;
        }
    }
}
