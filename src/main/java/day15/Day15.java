package day15;

import utils.Utils;

import java.io.IOException;
import java.util.*;

import static day15.EventType.RANGE_END;
import static day15.EventType.RANGE_START;

public class Day15 {

    public static void main(String[] args) throws IOException {
        var t0 = System.currentTimeMillis();
        var lines = Utils.readAllLines("inputs/test.txt");

        List<Sensor> sensors = new ArrayList<>();
        Map<Position, Beacon> beacons = new HashMap<>();
        for (var line : lines) {
            var data = line.substring("Sensor\sat\sx=".length()).split(":\sclosest\sbeacon\sis\sat\sx=");
            var sensor = new Sensor(data[0].split(",\sy="));

            var position = new Position(data[1].split(",\sy="));
            var beacon = beacons.getOrDefault(position, new Beacon(position));
            sensor.setBeacon(beacon);

            beacons.put(position, beacon);
            sensors.add(sensor);
        }

        part1(sensors, beacons);
        part2(sensors, beacons);
        System.out.println(System.currentTimeMillis() - t0);
    }

    static void part1(List<Sensor> sensors, Map<Position, Beacon> beacons) {
        int y = 2000000;
        List<Event> events = getEvents(sensors, beacons, y);

        System.out.println(sweep(events));
    }

    static void part2(List<Sensor> sensors, Map<Position, Beacon> beacons) {
        for (int y = 0; y < 4_000_001; y++) {
            List<Event> events = getEvents(sensors, beacons, y);
            List<Range> ranges = sweepList(events);
            var x = findX(ranges);
            if (x.isPresent()) {
                System.out.println(x.get() * 4_000_000L + y);
                return;
            }
        }
    }

    static Optional<Integer> findX(List<Range> ranges) {
        for (var range : ranges) {
            if (range.end().x() >= 0 && range.end().x() < 4_000_000) {
                return Optional.of(range.end().x() + 1);
            }
        }
        return Optional.empty();
    }

    static List<Event> getEvents(List<Sensor> sensors, Map<Position, Beacon> beacons, int y) {
        List<Event> events = new ArrayList<>();
        for (var sensor : sensors) {
            var range = sensor.getRangeAtY(y);
            if (range.isPresent()) {
                events.add(new Event(range.get().start(), RANGE_START));
                events.add(new Event(range.get().end(), RANGE_END));
            }
        }

        for (var position : beacons.keySet()) {
            if (position.y() == y) {
                events.add(new Event(position, EventType.BEACON));
            }
        }

        events.sort(((o1, o2) -> {
            if (o1.position().x() == o2.position().x()) {
                return Integer.compare(o1.eventType(), o2.eventType());
            } else {
                return Integer.compare(o1.position().x(), o2.position().x());
            }
        }));
        return events;
    }

    @SuppressWarnings("ConstantConditions")
    static int sweep(List<Event> events) {
        int numPositions = 0;
        int rangeLayer = 0;
        Position rangeStart = null;

        for (var event : events) {
            switch (event.eventType()) {
                case RANGE_START -> {
                    if (rangeLayer == 0) {
                        rangeStart = event.position();
                    }
                    rangeLayer++;
                }
                case RANGE_END -> {
                    rangeLayer--;
                    if (rangeLayer == 0) {
                        numPositions += event.position().x() + 1 - rangeStart.x();
                    }
                }
                default -> {
                    if (rangeLayer > 0) {
                        numPositions--;
                    }
                }
            }
        }

        return numPositions;
    }

    static List<Range> sweepList(List<Event> events) {
        int rangeLayer = 0;
        Position rangeStart = null;
        List<Range> ranges = new ArrayList<>();

        for (var event : events) {
            switch (event.eventType()) {
                case RANGE_START -> {
                    if (rangeLayer == 0) {
                        rangeStart = event.position();
                    }
                    rangeLayer++;
                }
                case RANGE_END -> {
                    rangeLayer--;
                    if (rangeLayer == 0) {
                        ranges.add(new Range(rangeStart, event.position()));
                    }
                }
            }
        }

        return ranges;
    }

    public static int manhattan(Position pos1, Position pos2) {
        return Math.abs(pos1.x() - pos2.x()) + Math.abs(pos1.y() - pos2.y());
    }
}
