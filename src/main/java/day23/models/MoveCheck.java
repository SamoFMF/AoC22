package day23.models;

import utils.Position;

public record MoveCheck(
    Position[] dirs,
    Position move
) {
}
