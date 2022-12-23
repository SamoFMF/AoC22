package day23.models;

import utils.Position;

public record MoveTo(
    Position start,
    Position end
) {
}
