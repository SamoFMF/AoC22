package day14;

import utils.Position;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cave {
    private int yMax;
    private int score;

    private final Map<Position, Character> blocks;
    private final Position start;
    private final List<Position> currentPath;

    public Cave() {
        yMax = -1;
        score = 0;

        blocks = new HashMap<>();
        start = new Position(500, 0);
        currentPath = new ArrayList<>();
        currentPath.add(start);
    }

    public void processLine(String line) {
        Position prevCoordinates = null;
        for (var coordinatesString : line.split("\s->\s")) {
            var coordinates = new Position(coordinatesString.split(","));
            addStraightLine(prevCoordinates, coordinates);
            prevCoordinates = coordinates;
        }
    }

    public int doSimulation1() { // Can only be run once (leaves behind sand block that's "in the abyss"
        yMax += 1;
        while (dropSand()) {
            score++;
        }
        return score;
    }

    public int doSimulation2() { // Must be run after Cave::doSimulation1
        score++; // We get 1 additional free sand block from part 1
        while (!blocks.containsKey(start)) {
            dropSand();
            score++;
        }

        return score;
    }

    private boolean dropSand() {
        var coordinates = currentPath.remove(currentPath.size() - 1);
        while (coordinates.y() < yMax) {
            Position coordinatesNext;
            if (!blocks.containsKey(new Position(coordinates.x(), coordinates.y() + 1))) {
                coordinatesNext = new Position(coordinates.x(), coordinates.y() + 1);
            } else if (!blocks.containsKey(new Position(coordinates.x() - 1, coordinates.y() + 1))) {
                coordinatesNext = new Position(coordinates.x() - 1, coordinates.y() + 1);
            } else if (!blocks.containsKey(new Position(coordinates.x() + 1, coordinates.y() + 1))) {
                coordinatesNext = new Position(coordinates.x() + 1, coordinates.y() + 1);
            } else {
                coordinatesNext = null;
            }

            if (coordinatesNext == null) {
                blocks.put(coordinates, 'o');
                return true;
            }

            blocks.remove(coordinates);
            blocks.put(coordinatesNext, 'o');
            currentPath.add(coordinates);
            coordinates = coordinatesNext;
        }

        return false;
    }

    private void addStraightLine(Position start, Position end) {
        if (start == null) {
            blocks.put(end, '#');

            if (end.y() > yMax) {
                yMax = end.y();
            }
            return;
        }

        int dx = Utils.sign(end.x() - start.x());
        int dy = Utils.sign(end.y() - start.y());
        var point = start;
        while (!end.equals(point)) {
            point = new Position(point.x() + dx, point.y() + dy);
            blocks.put(point, '#');

            if (point.y() > yMax) {
                yMax = point.y();
            }
        }
    }
}
