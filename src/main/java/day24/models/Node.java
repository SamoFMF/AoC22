package day24.models;

import utils.Position;

public record Node(
    Position position,
    int steps,
    int heuristics
) {
}
