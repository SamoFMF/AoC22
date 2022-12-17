package day17.rocks;

import utils.Position;

import java.util.Set;
import java.util.stream.IntStream;

public class Rock2 extends AbstractRock {

    public Rock2(int y, Set<Position> covered) {
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
                && notCoveredRelative(3, 0)
                && notCoveredRelative(3, 1)
                && notCoveredRelative(3, 2);
            case '<' -> anchor.x() > 0
                && notCoveredRelative(-1, 0)
                && notCoveredRelative(1, 1)
                && notCoveredRelative(1, 2);
            default -> anchor.y() > 0
                && IntStream
                .range(0, 3)
                .allMatch(i -> notCoveredRelative(i, -1));
        };
    }

    @Override
    protected void move(char dir) {
        switch (dir) {
            case '>' -> {
                covered.remove(anchor);
                removeRelative(2, 1);
                removeRelative(2, 2);

                anchor = anchor.addDelta(1, 0);

                IntStream
                    .range(0, 3)
                    .forEach(i -> addRelative(2, i));
            }
            case '<' -> {
                IntStream
                    .range(0, 3)
                    .forEach(i -> removeRelative(2, i));

                anchor = anchor.addDelta(-1, 0);

                covered.add(anchor);
                addRelative(2, 1);
                addRelative(2, 2);
            }
            default -> {
                removeRelative(2, 2);
                covered.remove(anchor);
                removeRelative(1, 0);

                anchor = anchor.addDelta(0, -1);

                covered.add(anchor);
                addRelative(1, 0);
                addRelative(2, 0);
            }
        }
    }
}
