package day17.rocks;

import utils.Position;

import java.util.Set;

public class Rock1 extends AbstractRock {

    public Rock1(int y, Set<Position> covered) {
        super(y, covered);
    }

    @Override
    public int getHeight() {
        return super.getHeight() + 2;
    }

    @Override
    protected boolean canMove(char dir) {
        return switch (dir) {
            case '>' -> anchor.x() < 4
                && notCoveredRelative(2, 2)
                && notCoveredRelative(3, 1)
                && notCoveredRelative(2, 0);
            case '<' -> anchor.x() > 0
                && notCoveredRelative(0, 2)
                && notCoveredRelative(-1, 1)
                && !covered.contains(anchor);
            default -> anchor.y() > 0
                && !covered.contains(anchor)
                && notCoveredRelative(2, 0)
                && notCoveredRelative(1, -1);
        };
    }

    @Override
    protected void move(char dir) {
        switch (dir) {
            case '>' -> {
                removeRelative(1, 2);
                removeRelative(0, 1);
                removeRelative(1, 0);

                anchor = anchor.addDelta(1, 0);

                addRelative(1, 2);
                addRelative(2, 1);
                addRelative(1, 0);
            }
            case '<' -> {
                removeRelative(1, 2);
                removeRelative(2, 1);
                removeRelative(1, 0);

                anchor = anchor.addDelta(-1, 0);

                addRelative(1, 2);
                addRelative(0, 1);
                addRelative(1, 0);
            }
            default -> {
                removeRelative(1, 2);
                removeRelative(0, 1);
                removeRelative(2, 1);

                anchor = anchor.addDelta(0, -1);

                addRelative(1, 0);
                addRelative(0, 1);
                addRelative(2, 1);
            }
        }
    }
}
