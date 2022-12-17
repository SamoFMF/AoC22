package utils;

public record Position(
    int x,
    int y
) {
    public Position addDelta(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }

    public Position(String[] values) {
        this(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }
}
