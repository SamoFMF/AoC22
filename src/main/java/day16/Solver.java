package day16;

import day16.models.Node;
import day16.models.Player;
import day16.models.Valve;

import java.util.*;

public class Solver {
    private final Map<String, Valve> valves;
    private final List<Valve> usefulValves;

    public Solver(Map<String, Valve> valves) {
        this.valves = valves;

        usefulValves = new ArrayList<>();
        for (var valve : this.valves.values()) {
            if (valve.getRate() > 0) {
                usefulValves.add(valve);
            }
        }
    }

    public int solve(String startId, int startTime, int numSolvers) {
        var players = new Player[numSolvers];
        var valve = this.valves.get(startId);
        for (int i = 0; i < numSolvers; i++) {
            players[i] = new Player(valve, 0);
        }
        return aStar(
            players,
            startTime
        );
    }

    private int aStar(
        Player[] initialPlayers,
        int initialTime
    ) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(o -> -o.pressure() - o.heuristics()));

        queue.add(
            new Node(
                initialPlayers,
                initialTime,
                0,
                0,
                new HashSet<>()
            )
        );

        while (!queue.isEmpty()) {
            var node = queue.poll();

            if (node.time() <= 0) {
                return node.pressure();
            }

            var players = node.players();
            if (players.length == 2 && players[0].isReady() && players[1].isReady()) {
                for (var valve1 : usefulValves) {
                    if (node.openedValves().contains(valve1)) {
                        continue;
                    }

                    for (var valve2 : usefulValves) {
                        if (valve1 == valve2 || node.openedValves().contains(valve2)) {
                            continue;
                        }

                        var newPlayers = new Player[2];
                        newPlayers[0] = players[0].getPlayer(valve1);
                        newPlayers[1] = players[1].getPlayer(valve2);

                        queue.add(
                            getNode(
                                newPlayers,
                                node.time(),
                                node.pressure(),
                                node.openedValves(),
                                new Valve[]{valve1, valve2}
                            )
                        );
                    }
                }
            } else {
                var playerIdx = players[0].isReady() ? 0 : 1;
                var everythingOpen = true;
                for (var valve : usefulValves) {
                    if (node.openedValves().contains(valve)) {
                        continue;
                    }

                    everythingOpen = false;
                    var newPlayers = new Player[players.length];
                    if (players.length == 1) {
                        newPlayers[0] = players[0].getPlayer(valve);
                    } else {
                        newPlayers[(playerIdx + 1) % 2] = players[(playerIdx + 1) % 2].getCopy();
                        newPlayers[playerIdx] = players[playerIdx].getPlayer(valve);
                    }

                    queue.add(
                        getNode(
                            newPlayers,
                            node.time(),
                            node.pressure(),
                            node.openedValves(),
                            new Valve[]{valve}
                        )
                    );
                }

                if (everythingOpen) {
                    var player = players[(playerIdx + 1) % players.length];
                    if (player.isReady()) {
                        return node.pressure();
                    } else {
                        return node.pressure() + Math.max(0, node.time() - player.getReadyIn()) * player.getValve().getRate();
                    }
                }
            }
        }


        return -1;
    }

    private Node getNode(
        Player[] players,
        int time,
        int pressure,
        Set<Valve> openedValves,
        Valve[] valves1
    ) {
        var timeDelta = Arrays.stream(players)
            .mapToInt(Player::getReadyIn)
            .min()
            .orElse(Integer.MAX_VALUE);

        Arrays.stream(players)
            .forEach(p -> p.passTime(timeDelta));

        var newRate = Arrays.stream(players)
            .filter(Player::isReady)
            .mapToInt(p -> p.getValve().getRate())
            .sum();

        var newTime = time - timeDelta;

        var newOpenedValves = new HashSet<>(openedValves);
        newOpenedValves.addAll(Arrays.asList(valves1));

        var newHeuristics = getHeuristics(
            players,
            newTime,
            newOpenedValves
        );

        return new Node(
            players,
            newTime,
            pressure + Math.max(0, newTime) * newRate,
            newHeuristics,
            newOpenedValves
        );
    }

    private int getHeuristics(Player[] players, int time, Set<Valve> openedValves) {
        int score = 0;
        for (var valve : usefulValves) {
            if (!openedValves.contains(valve)) {
                var newTime = Arrays.stream(players)
                    .mapToInt(p -> time - p.getReadyIn() - p.getValve().getDistances().get(valve) - 1)
                    .max()
                    .orElse(0);

                score += Math.max(0, newTime) * valve.getRate();
            }
        }

        for (var player : players) {
            if (!player.isReady()) {
                score += Math.max(0, time - player.getReadyIn()) * player.getValve().getRate();
            }
        }

        return score;
    }
}
