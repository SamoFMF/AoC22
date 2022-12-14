package day14;

public record Coordinates(
    int x,
    int y
) {
    public Coordinates(String[] values) {
        this(
            Integer.parseInt(values[0]),
            Integer.parseInt(values[1])
        );
    }
}
