package day15;

import utils.Position;

public record Event(
    Position position,
    int eventType
) {
}
