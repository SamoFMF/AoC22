package day18;

import java.util.*;

public class Cubes {
    private int surfaceArea;

    private final Set<Position> cubes;
    private final Map<Position, Boolean> layer;
    private final Position[] directions;
    private final int[] xLim;
    private final int[] yLim;
    private final int[] zLim;

    public Cubes() {
        surfaceArea = 0;

        cubes = new HashSet<>();
        layer = new HashMap<>();

        xLim = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        yLim = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        zLim = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};

        directions = new Position[]{
            new Position(1, 0, 0),
            new Position(-1, 0, 0),
            new Position(0, 1, 0),
            new Position(0, -1, 0),
            new Position(0, 0, 1),
            new Position(0, 0, -1)
        };
    }

    public int getSurfaceArea() {
        return surfaceArea;
    }

    public void addCube(Position cube) {
        if (!cubes.contains(cube)) {
            surfaceArea += 6;
            for (var dir : directions) {
                if (cubes.contains(cube.addPosition(dir))) {
                    surfaceArea -= 2;
                }
            }
        }

        cubes.add(cube);

        if (cube.x() < xLim[0]) xLim[0] = cube.x();
        if (cube.x() > xLim[1]) xLim[1] = cube.x();
        if (cube.y() < yLim[0]) yLim[0] = cube.y();
        if (cube.y() > yLim[1]) yLim[1] = cube.y();
        if (cube.z() < zLim[0]) zLim[0] = cube.z();
        if (cube.z() > zLim[1]) zLim[1] = cube.z();
    }

    public int findPockets() {
        var insideArea = 0;
        for (int z = zLim[0]; z < zLim[1] + 1; z++) {
            for (int y = yLim[0]; y < yLim[1] + 1; y++) {
                for (int x = xLim[0]; x < xLim[1] + 1; x++) {
                    var cube = new Position(x, y, z);
                    if (!cubes.contains(cube) && !isOutside(cube)) {
                        insideArea += getNumNeighbours(cube);
                    }
                }
            }
        }
        return insideArea;
    }

    private boolean isOutside(Position cube) {
        if (layer.containsKey(cube)) {
            return layer.get(cube);
        }

        Set<Position> visited = new HashSet<>();
        List<Position> toCheck = new ArrayList<>();
        toCheck.add(cube);
        visited.add(cube);
        while (!toCheck.isEmpty()) {
            List<Position> next = new ArrayList<>();

            for (var pos : toCheck) {
                for (var dir : directions) {
                    var pos1 = pos.addPosition(dir);
                    if (!cubes.contains(pos1) && !visited.contains(pos1)) {
                        if (layer.containsKey(pos1)) {
                            return updateLayer(layer.get(pos1), visited);
                        } else {
                            next.add(pos1);
                            visited.add(pos1);
                            if (!withinLimits(pos1)) {
                                return updateLayer(true, visited);
                            }
                        }
                    }
                }
            }

            toCheck = next;
        }

        return updateLayer(false, visited);
    }

    private boolean updateLayer(boolean isOutside, Set<Position> visited) {
        for (var cube : visited) {
            layer.put(cube, isOutside);
        }

        return isOutside;
    }

    private boolean withinLimits(Position cube) {
        return cube.x() >= xLim[0] && cube.x() <= xLim[1]
            && cube.y() >= yLim[0] && cube.y() <= yLim[1]
            && cube.z() >= zLim[0] && cube.z() <= zLim[1];
    }

    private int getNumNeighbours(Position cube) {
        var numNeighbours = 0;
        for (var dir : directions) {
            if (cubes.contains(cube.addPosition(dir))) {
                numNeighbours++;
            }
        }
        return numNeighbours;
    }
}
