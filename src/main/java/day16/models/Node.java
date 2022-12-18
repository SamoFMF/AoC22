package day16.models;

import java.util.Set;

public record Node(
    Player[] players,
    int time,
    int pressure,
    int heuristics,
    Set<Valve> openedValves
) {
}
