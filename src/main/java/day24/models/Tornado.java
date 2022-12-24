package day24.models;

import utils.Position;

public record Tornado(
    Position position,
    Position direction
) {
}
