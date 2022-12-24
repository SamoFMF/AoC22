package day24.models;

import utils.Position;

public record Visited(
    Position position,
    int steps
) {
}
