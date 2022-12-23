package day23;

import day23.models.MoveCheck;
import day23.models.MoveTo;
import utils.Position;

import java.util.*;

public class Solver {
    final Set<Position> grid;
    final Position[] directions;
    final Deque<MoveCheck> moveChecks;

    private int curRow;

    public Solver() {
        grid = new HashSet<>();

        directions = new Position[]{
            new Position(-1, -1),
            new Position(0, -1),
            new Position(1, -1),
            new Position(1, 0),
            new Position(1, 1),
            new Position(0, 1),
            new Position(-1, 1),
            new Position(-1, 0)
        };

        moveChecks = new ArrayDeque<>();
        for (var i : new int[]{0, 2, 3, 1}) {
            moveChecks.add(
                new MoveCheck(
                    new Position[]
                        {
                            directions[2 * i],
                            directions[2 * i + 1],
                            directions[(2 * i + 2) % directions.length]
                        },
                    directions[2 * i + 1]
                )
            );
        }

        curRow = 0;
    }

    public int getGridSize() {
        return grid.size();
    }

    public void addRow(String line) {
        for (int col = 0; col < line.length(); col++) {
            if (line.charAt(col) == '#') {
                grid.add(new Position(col, curRow));
            }
        }
        curRow++;
    }

    public boolean playRound() {
        // First half
        var considerMoving = getConsiderMoving();
        if (considerMoving.size() == 0) {
            return false;
        }
        var moveTos = getMoveTos(considerMoving);

        // Second half
        for (var moveToList : moveTos.values()) {
            if (moveToList.size() == 1) {
                var moveTo = moveToList.get(0);
                grid.remove(moveTo.start());
                grid.add(moveTo.end());
            }
        }

        // Finally
        moveChecks.add(moveChecks.removeFirst());

        return true;
    }

    public int[] getExtremes() {
        int[] extremes = new int[] // {xMin,xMax,yMin,yMax}
            {
                Integer.MAX_VALUE,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                Integer.MIN_VALUE
            };
        for (var pos : grid) {
            if (pos.x() < extremes[0]) extremes[0] = pos.x();
            if (pos.x() > extremes[1]) extremes[1] = pos.x();
            if (pos.y() < extremes[2]) extremes[2] = pos.y();
            if (pos.y() > extremes[3]) extremes[3] = pos.y();
        }
        return extremes;
    }

    private Set<Position> getConsiderMoving() {
        Set<Position> result = new HashSet<>();
        for (var pos : grid) {
            if (hasNeighbour(pos)) {
                result.add(pos);
            }
        }
        return result;
    }

    private Map<Position, List<MoveTo>> getMoveTos(Set<Position> considerMoving) {
        Map<Position, List<MoveTo>> moveTos = new HashMap<>();
        for (var pos : considerMoving) {
            getMoveTo(pos)
                .ifPresent(moveTo -> {
                    if (!moveTos.containsKey(moveTo.end())) {
                        moveTos.put(moveTo.end(), new ArrayList<>());
                    }

                    moveTos.get(moveTo.end()).add(moveTo);
                });
        }

        return moveTos;
    }

    private boolean hasNeighbour(Position position) {
        for (var dir : directions) {
            if (grid.contains(position.addPosition(dir))) {
                return true;
            }
        }

        return false;
    }

    private Optional<MoveTo> getMoveTo(Position position) {
        for (var moveCheck : moveChecks) {
            if (doMoveCheck(position, moveCheck)) {
                return Optional.of(
                    new MoveTo(
                        position,
                        position.addPosition(moveCheck.move())
                    )
                );
            }
        }

        return Optional.empty();
    }

    private boolean doMoveCheck(Position position, MoveCheck moveCheck) {
        for (var dir : moveCheck.dirs()) {
            if (grid.contains(position.addPosition(dir))) {
                return false;
            }
        }

        return true;
    }
}
