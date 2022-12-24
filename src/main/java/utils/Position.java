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

    public Position addPositionK(Position position, int k) {
        return new Position(x + k * position.x(), y + k * position.y());
    }

    public Position(String[] values) {
        this(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    public int manhattan(Position position) {
        return Math.abs(x - position.x()) + Math.abs(y - position.y());
    }
}
