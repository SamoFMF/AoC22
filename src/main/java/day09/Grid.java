package day09;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    private final int[][] knots;
    private final Set<Coordinates> visited;

    public Grid(int numKnots) {
        this.knots = new int[numKnots][2];

        visited = new HashSet<>();
        visited.add(new Coordinates(0, 0));
    }

    public void handleLine(String line) {
        var data = line.split(" ");
        var direction = Direction.getDirection(data[0]);

        move(knots[0], direction, Integer.parseInt(data[1]));
        handleKnots();
    }

    public int getNumVisitedPositions() {
        return visited.size();
    }

    private void move(int[] knot, Direction direction, int steps) {
        knot[0] += steps * direction.dx();
        knot[1] += steps * direction.dy();
    }

    private void handleKnots() {
        while (Grid.getDistance(knots[0], knots[1]) > 1) {
            var prevKnot = knots[0];
            for (int i = 1; i < knots.length; i++) {
                var knot = knots[i];
                if (Grid.getDistance(prevKnot, knot) <= 1) {
                    prevKnot = null;
                    break;
                }

                var direction = new Direction(Grid.sign(prevKnot[0] - knot[0]), Grid.sign(prevKnot[1] - knot[1]));
                move(knot, direction, 1);
                prevKnot = knot;
            }

            if (prevKnot != null) {
                visited.add(new Coordinates(prevKnot[0], prevKnot[1]));
            }
        }
    }

    private static int getDistance(int[] head, int[] tail) {
        return Math.max(Math.abs(head[0] - tail[0]), Math.abs(head[1] - tail[1]));
    }

    private static int sign(int x) {
        if (x == 0) {
            return x;
        } else if (x > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
