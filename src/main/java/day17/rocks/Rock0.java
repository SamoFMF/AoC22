package day17.rocks;

import utils.Position;

import java.util.Set;
import java.util.stream.IntStream;

public class Rock0 extends AbstractRock {

    public Rock0(int y, Set<Position> covered) {
        super(y, covered);
    }

    @Override
    protected boolean canMove(char dir) {
        return switch (dir) {
            case '>' -> anchor.x() < 3 && notCoveredRelative(4, 0);
            case '<' -> anchor.x() > 0 && notCoveredRelative(-1, 0);
            default -> anchor.y() > 0
                && IntStream
                .range(0, 4)
                .allMatch(x -> notCoveredRelative(x, -1));
        };
    }

    @Override
    protected void move(char dir) {
        switch (dir) {
            case '>' -> {
                covered.remove(anchor);
                addRelative(4, 0);
                anchor = anchor.addDelta(1, 0);
            }
            case '<' -> {
                removeRelative(3, 0);
                anchor = anchor.addDelta(-1, 0);
                covered.add(anchor);
            }
            default -> {
                IntStream
                    .range(0, 4)
                    .forEach(i -> removeRelative(i, 0));

                anchor = anchor.addDelta(0, -1);

                IntStream
                    .range(0, 4)
                    .forEach(i -> addRelative(i, 0));
            }
        }
    }
}
