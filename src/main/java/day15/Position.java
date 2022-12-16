package day15;

public record Position(
    int x,
    int y
) {
    public Position(String[] xy) {
        this(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
    }
}
