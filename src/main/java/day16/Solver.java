package day16;

import java.util.*;

public class Solver {
    private final Map<String, Valve> valves;

    public Solver(Map<String, Valve> valves) {
        this.valves = valves;
    }

    public int solve(String startId, int startTime, int numSolvers) {
        var valves = new Valve[numSolvers];
        var valve = this.valves.get(startId);
        var times = new int[numSolvers];
        for (int i = 0; i < numSolvers; i++) {
            valves[i] = valve;
            times[i] = startTime;
        }
        return recursiveSolver(
            valves,
            times,
            0
        );
    }

    private int recursiveSolver(
        Valve[] valves,
        int[] times,
        int pressure
    ) {
        var i = 1;
        if (times.length == 1 || times[0] > 0) i = 0;
        var i2 = 1 - i;

        var time = times[i];

        var curValve = valves[i];
        var distances = curValve.getDistances();

        // Choose valve to open
        var maxScore = 0;
        for (var valve : distances.keySet()) {
            var newTime = time - (distances.get(valve) + 1);
            if (newTime > valve.getOpenedAt()) {
                var delta = valve.openValve(newTime);
                var score = recursiveSolver(
                    i2 < valves.length ? new Valve[]{valves[i2], valve} : new Valve[]{valve},
                    i2 < valves.length ? new int[]{times[i2], newTime} : new int[]{newTime},
                    delta * valve.getRate()
                );
                valve.closeValve();

                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }

        return maxScore + pressure;
    }
}
