package day09;

public record Direction(
    int dx,
    int dy
) {
    public static Direction getDirection(String dir) {
        return switch (dir) {
            case "R" -> new Direction(1, 0);
            case "L" -> new Direction(-1, 0);
            case "U" -> new Direction(0, 1);
            default -> new Direction(0, -1);
        };
    }
}
