package day15;

import java.util.Optional;

public class Sensor {
    private Beacon beacon;

    private final Position position;

    public Sensor(String[] xy) {
        position = new Position(xy);
    }

    public Position getPosition() {
        return position;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }

    public Optional<Range> getRangeAtY(int y) {
        int dBeacon = Day15.manhattan(position, beacon.getPosition());

        int dy;
        if ((dy = Math.abs(position.y()-y)) > dBeacon) {
            return Optional.empty();
        }

        return Optional.of(new Range(new Position(position.x()-(dBeacon-dy), y), new Position(position.x()+(dBeacon-dy), y)));
    }
}
