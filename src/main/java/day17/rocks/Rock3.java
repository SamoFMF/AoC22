package day17.rocks;

import utils.Position;

import java.util.Set;
import java.util.stream.IntStream;

public class Rock3 extends AbstractRock {

    public Rock3(int y, Set<Position> covered) {
        super(y, covered);
    }

    @Override
    public int getHeight() {
        return super.getHeight() + 3;
    }

    @Override
    protected boolean canMove(char dir) {
        return switch (dir) {
            case '>' -> anchor.x() < 6
                && IntStream
                .range(0, 4)
                .allMatch(i -> notCoveredRelative(1, i));
            case '<' -> anchor.x() > 0
                && IntStream
                .range(0, 4)
                .allMatch(i -> notCoveredRelative(-1, i));
            default -> anchor.y() > 0
                && notCoveredRelative(0, -1);
        };
    }

    @Override
    protected void move(char dir) {
        switch (dir) {
            case '>' -> {
                IntStream
                    .range(0, 4)
                    .forEach(i -> removeRelative(0, i));

                anchor = anchor.addDelta(1, 0);

                IntStream
                    .range(0, 4)
                    .forEach(i -> addRelative(0, i));
            }
            case '<' -> {
                IntStream
                    .range(0, 4)
                    .forEach(i -> removeRelative(0, i));

                anchor = anchor.addDelta(-1, 0);

                IntStream
                    .range(0, 4)
                    .forEach(i -> addRelative(0, i));
            }
            default -> {
                removeRelative(0, 3);

                anchor = anchor.addDelta(0, -1);

                covered.add(anchor);
            }
        }
    }
}
