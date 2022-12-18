package day16.models;

import java.util.*;

public class Valve {
    private final String id;
    private final int rate;
    private final String[] neighbours;
    private final Map<Valve, Integer> distances;

    public Valve(String line) {
        var data = line.split(";\stunnels?\sleads?\sto\svalves?\s");
        id = data[0].substring("Valve\s".length(), "Valve\s".length() + 2);
        rate = Integer.parseInt(data[0].substring("Valve\sAA\shas\sflow\srate=".length()));
        neighbours = data[1].split(",\s");

        distances = new HashMap<>();
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
        toAdd.add(this);

        var d = 0;
        Set<Valve> visited = new HashSet<>();
        visited.add(this);

        while (toAdd.size() > 0 && distances.size() < valves.size()) {
            List<Valve> next = new ArrayList<>();
            for (var valve : toAdd) {
                if (valve.getRate() > 0) {
                    distances.put(valve, d);
                }
                for (var neighbour : valve.getNeighbours()) {
                    var nValve = valves.get(neighbour);
                    if (!visited.contains(nValve)) {
                        next.add(nValve);
                        visited.add(nValve);
                    }
                }
            }
            toAdd = next;
            d++;
        }
    }
}
