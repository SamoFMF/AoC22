package day18;

public record Position(
    int x,
    int y,
    int z
) {
    public Position(String[] values) {
        this(
            Integer.parseInt(values[0]),
            Integer.parseInt(values[1]),
            Integer.parseInt(values[2])
        );
    }

    public Position addPosition(Position position) {
        return new Position(
            x + position.x,
            y + position.y,
            z + position.z
        );
    }
}
