package day12;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Heightmap {
    private final char[][] heightmap;
    private Position start, end;
    private Function<Character, Double> factor;
    BiFunction<Character, Character, Boolean> comparator;

    public Heightmap(List<String> lines) {
        heightmap = new char[lines.size()][];

        fillHeightmap(lines);
    }

    public void setComparator(BiFunction<Character, Character, Boolean> comparator) {
        this.comparator = comparator;
    }

    public void setFactor(Function<Character, Double> factor) {
        this.factor = factor;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public int findShortestPath(
        Position start,
        Function<Node, Boolean> isEndNode
    ) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::score));

        Set<Position> visited = new HashSet<>();

        queue.add(getNode(start, 0));
        while (!queue.isEmpty()) {
            var node = queue.poll();

            if (isEndNode.apply(node)) {
                return node.steps();
            }

            var height = getAtPosition(node.position());
            addToQueueIfRequirementsMet(queue, visited, node, height, 1, 0);
            addToQueueIfRequirementsMet(queue, visited, node, height, -1, 0);
            addToQueueIfRequirementsMet(queue, visited, node, height, 0, 1);
            addToQueueIfRequirementsMet(queue, visited, node, height, 0, -1);
        }

        throw new RuntimeException("Did not reach end!");
    }

    private void addToQueueIfRequirementsMet(
        PriorityQueue<Node> queue,
        Set<Position> visited,
        Node node,
        char height,
        int drow,
        int dcol
    ) {
        Position pos;
        if (this.comparator.apply(getAtPosition(pos = new Position(node.position().row() + drow, node.position().col() + dcol)), height) && !visited.contains(pos)) {
            queue.add(getNode(pos, node.steps() + 1));
            visited.add(pos);
        }
    }

    public char getAtPosition(Position position) {
        if (position.row() < 0 || position.row() >= heightmap.length || position.col() < 0 || position.col() >= heightmap[position.row()].length) {
            return 255;
        }

        return heightmap[position.row()][position.col()];
    }

    private Node getNode(Position position, int steps) {
        return new Node(position, getWeight(position, steps), steps);
    }

    private double getWeight(Position position, int steps) {
        return steps + (Math.abs(end.row() - position.row()) + Math.abs(end.col() - position.col())) * factor.apply(getAtPosition(position));
    }

    private void fillHeightmap(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            fillRow(i, lines.get(i));
        }
    }

    private void fillRow(int row, String line) {
        var mapRow = line.toCharArray();
        heightmap[row] = mapRow;

        for (int col = 0; col < mapRow.length; col++) {
            if (mapRow[col] == 'S') {
                start = new Position(row, col);
                mapRow[col] = 'a';
            } else if (mapRow[col] == 'E') {
                end = new Position(row, col);
                mapRow[col] = 'z';
            }
        }
    }
}
