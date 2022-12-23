package utils;

public record Position(
    int x,
    int y
) {
    public Position addDelta(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public Position addPosition(Position position) {
        return new Position(x + position.x(), y + position.y());
    }

    public Position(String[] values) {
        this(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }
}
