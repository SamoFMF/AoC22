package day22;

import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private static final int N = 50;

    private final List<Row> grid;
    private final List<CubeLayer> cubeLayers;

    Me me;

    public Grid() {
        grid = new ArrayList<>();
        cubeLayers = new ArrayList<>();
    }

    public List<Row> getGrid() {
        return grid;
    }

    public Me getMe() {
        return me;
    }

    public long getScore() {
        var position = me.getPosition();
        var direction = me.getDirection();

        return 1000L * (position.y() + 1) + 4L * (position.x() + 1) + Math.abs(direction.x() + 2L * direction.y() - 1L);
    }

    public void addRow(String line) {
        grid.add(new Row(line, grid.size()));
    }

    public void moveToStart() {
        me = new Me(new Position(grid.get(0).getStart(), 0), new Position(1, 0));
    }

    public void addCubeLayer(CubeLayer cubeLayer) {
        cubeLayers.add(cubeLayer);
    }

    public void move(String move, boolean isPart1) {
        switch (move) {
            case "R" -> me.turnRight();
            case "L" -> me.turnLeft();
            default -> {
                var steps = Integer.parseInt(move);
                if (isPart1) {
                    moveFor1(steps);
                } else {
                    moveFor2(steps);
                }
            }
        }
    }

    private void moveFor1(int steps) {
        for (int i = 0; i < steps; i++) {
            var next = me.getNext();
            next = updateNextRow(next);
            next = updateNextCol(next);
            if (canMove(next)) {
                me.moveTo(next);
            } else {
                break;
            }
        }
    }

    private void moveFor2(int steps) {
        for (int i = 0; i < steps; i++) {
            var cubeLayer = getCubeLayer(me.getPosition());
            var next = cubeLayer.getNext(me.getNext(), me.getDirection());
            var nextCubeLayer = getCubeLayer(next.position());
            if (nextCubeLayer.getAt(next.position()) == '#') {
                break;
            } else {
                me.update(next.position(), next.direction());
            }
        }
    }

    private CubeLayer getCubeLayer(Position position) {
        for (var cubeLayer : cubeLayers) {
            if (cubeLayer.contains(position)) {
                return cubeLayer;
            }
        }

        throw new RuntimeException("Position " + position + " not contained in any cube layer!");
    }

    private Row getCurrentRow() {
        return grid.get(me.getPosition().y());
    }

    private Row getRow(Position position) {
        return grid.get(position.y());
    }

//    private UpdateMe processNext(Position start, Position next) {
//        var xs = start.x();
//        var ys = start.y();
//        var x = next.x();
//        var y = next.y();
//
//        if (y >= 0 && y < grid.size() && grid.get(y).withinLimits(x)) {
//            return new UpdateMe(next, me.getDirection());
//        }
//
//        Position pos;
//        Position dir;
//        if (y == -1) {
//            if (x < grid.get(0).getStart() + N) {
//                pos = new Position(0, 3 * N + xs);
//                dir = new Position(1, 0);
//            } else {
//                pos = new Position(xs - 2 * N, 4 * N - 1);
//                dir = new Position(0, -1);
//            }
//        } else if (y == 4 * N) {
//            pos = new Position(xs + 2 * N, 0);
//            dir = new Position(0, 1);
//        } else if (x < grid.get(y).getStart()) {
//            if (y < N) {
//                pos = new Position(0, 3 * N - 1 - ys);
//                dir = new Position(1, 0);
//            } else if (y < 2 * N) {
//                pos = new Position(ys - N, 2 * N);
//                dir = new Position(0, 1);
//            } else if (y < 3 * N) {
//                pos = new Position(0, 3 * N - 1 - ys);
//                dir = new Position(1, 0);
//            } else {
//                pos = new Position(ys - 3 * N, 0);
//                dir = new Position(0, 1);
//            }
//        } else if (x > grid.get(y).getEnd()) {
//            if (y < N) {
//                pos = new Position(2 * N - 1, 3 * N - 1 - ys);
//                dir = new Position(-1, 0);
//            } else if (y < 2 * N) {
//                pos = new Position(ys + N, N - 1);
//                dir = new Position(0, -1);
//            } else if (y < 3 * N) {
//                pos = new Position(3 * N - 1, 3 * N - 1 - ys);
//                dir = new Position(-1, 0);
//            } else {
//                pos = new Position(ys - 2 * N, 3 * N - 1);
//                dir = new Position(0, -1);
//            }
//        } else if ()
//
//        return new UpdateMe(pos, dir);
//    }

    private boolean canMove(Position position) {
        return grid.get(position.y()).getAt(position.x()) == '.';
    }

    private Position updateNextRow(Position next) {
        if (next.y() < 0 || next.y() >= grid.size() || !grid.get(next.y()).withinLimits(next.x())) {
            var dir = getReverseDirection(me.getDirection());
            var cur = next;
            do {
                cur = next;
                next = next.addDelta(dir.x(), dir.y());
            } while (next.y() >= 0 && next.y() < grid.size() && grid.get(next.y()).withinLimits(next.x()));
            return cur;
        }

        return next;
    }

    private Position updateNextCol(Position next) {
        var row = grid.get(next.y());
        if (!row.withinLimits(next.x())) {
            return new Position(row.getStart(), next.y());
        }

        return next;
    }

    private static Position getReverseDirection(Position direction) {
        return new Position(-direction.x(), -direction.y());
    }
}
