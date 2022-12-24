package day24;

import day24.models.Node;
import day24.models.Tornado;
import day24.models.TornadoList;
import day24.models.Visited;
import utils.Position;
import utils.Utils;

import java.util.*;

public class Solver {
    private final Set<Position> wall;
    private final TornadoList[] tornadoRows;
    private final TornadoList[] tornadoCols;
    private final Position[] moves;
    private final Position start;
    private final Position end;
    private final int xMax;
    private final int yMax;

    public Solver(Position start, Position end, int xMax, int yMax) {
        wall = new HashSet<>();
        tornadoRows = new TornadoList[yMax + 1];
        for (int i = 0; i < tornadoRows.length; i++) tornadoRows[i] = new TornadoList();
        tornadoCols = new TornadoList[xMax + 1];
        for (int i = 0; i < tornadoCols.length; i++) tornadoCols[i] = new TornadoList();

        moves = new Position[]{
            new Position(0, 0),
            new Position(1, 0),
            new Position(0, 1),
            new Position(-1, 0),
            new Position(0, -1)
        };

        this.start = start;
        this.end = end;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public void addTornado(Position position, Position dir) {
        var tornado = new Tornado(position, dir);
        tornadoRows[position.y()].add(tornado);
        tornadoCols[position.x()].add(tornado);
    }

    public void addWallBlock(Position position) {
        wall.add(position);
    }

    public int solve(int steps, boolean reverse) {
        Position start, end;
        if (reverse) {
            start = this.end;
            end = this.start;
        } else {
            start = this.start;
            end = this.end;
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.steps() + o.heuristics()));
        queue.add(new Node(start, steps, 0));

        Set<Visited> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            var node = queue.poll();

            if (end.equals(node.position())) {
                return node.steps();
            }

            // Check possible moves
            for (var dir : moves) {
                var pos = node.position().addPosition(dir);
                if (!wall.contains(pos) && isAvailable(pos, node.steps() + 1)) {
                    var visit = new Visited(pos, node.steps() + 1);
                    if (visited.contains(visit)) continue;
                    visited.add(visit);
                    queue.add(
                        new Node(
                            pos,
                            node.steps() + 1,
                            getHeuristics(pos, end)
                        )
                    );
                }
            }
        }

        throw new RuntimeException("Queue ran out of items before finding the end.");
    }

    private boolean isAvailable(Position position, int steps) {
        return isAvailableList(position, steps, tornadoCols[position.x()])
            && isAvailableList(position, steps, tornadoRows[position.y()]);
    }

    private boolean isAvailableList(Position position, int steps, TornadoList tornadoes) {
        for (var tornado : tornadoes) {
            var pos = tornado.position().addPositionK(tornado.direction(), steps);
            pos = applyMod(pos, tornado.direction().x() != 0);
            if (position.equals(pos)) {
                return false;
            }
        }

        return true;
    }

    private Position applyMod(Position position, boolean applyOnX) {
        if (applyOnX) {
            return new Position(
                Utils.mod(position.x() - 1, xMax - 1) + 1,
                position.y()
            );
        } else {
            return new Position(
                position.x(),
                Utils.mod(position.y() - 1, yMax - 1) + 1
            );
        }
    }

    private int getHeuristics(Position position, Position end) {
        return end.manhattan(position);
    }
}
