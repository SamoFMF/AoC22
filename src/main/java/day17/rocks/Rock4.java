package day17.rocks;

import utils.Position;

import java.util.Set;

public class Rock4 extends AbstractRock {

    public Rock4(int y, Set<Position> covered) {
        super(y, covered);
    }

    @Override
    public int getHeight() {
        return super.getHeight() + 1;
    }

    @Override
    protected boolean canMove(char dir) {
        return switch (dir) {
            case '>' -> anchor.x() < 5
                && notCoveredRelative(2, 0)
                && notCoveredRelative(2, 1);
            case '<' -> anchor.x() > 0
                && notCoveredRelative(-1, 0)
                && notCoveredRelative(-1, 1);
            default -> anchor.y() > 0
                && notCoveredRelative(0, -1)
                && notCoveredRelative(1, -1);
        };
    }

    @Override
    protected void move(char dir) {
        switch (dir) {
            case '>' -> {
                covered.remove(anchor);
                removeRelative(0, 1);

                anchor = anchor.addDelta(1, 0);

                addRelative(1, 0);
                addRelative(1, 1);
            }
            case '<' -> {
                removeRelative(1, 0);
                removeRelative(1, 1);

                anchor = anchor.addDelta(-1, 0);

                covered.add(anchor);
                addRelative(0, 1);
            }
            default -> {
                removeRelative(0, 1);
                removeRelative(1, 1);

                anchor = anchor.addDelta(0, -1);

                covered.add(anchor);
                addRelative(1, 0);
            }
        }
    }
}
