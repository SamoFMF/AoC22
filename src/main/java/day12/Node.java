package day12;

import utils.Position;

public record Node(
    Position position,
    double score,
    int steps
) {
}
