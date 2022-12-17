package day17.rocks;

import utils.Position;

import java.util.Set;

public abstract class AbstractRock {
    protected final Set<Position> covered;

    protected Position anchor; // Most bottom left point

    public AbstractRock(int y, Set<Position> covered) {
        anchor = new Position(2, y);
        this.covered = covered;
    }

    public int getHeight() {
        return anchor.y();
    }

    public boolean tryMove(char dir) {
        if (canMove(dir)) {
            move(dir);
            return true;
        }

        return false;
    }

    protected boolean notCoveredRelative(int dx, int dy) {
        return !covered.contains(anchor.addDelta(dx, dy));
    }

    protected void removeRelative(int dx, int dy) {
        covered.remove(anchor.addDelta(dx, dy));
    }

    protected void addRelative(int dx, int dy) {
        covered.add(anchor.addDelta(dx, dy));
    }

    protected abstract boolean canMove(char dir);

    protected abstract void move(char dir);
}
