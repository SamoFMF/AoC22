package day04;

public class Range {
    private final int start;
    private final int end;

    public Range(String range) {
        var endpoints = range.split("-");

        start = Integer.parseInt(endpoints[0]);
        end = Integer.parseInt(endpoints[1]);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean fullyContains(Range range) {
        return start <= range.getStart() && end >= range.getEnd();
    }

    public boolean overlapsOnRight(Range range) {
        return start <= range.getStart() && end >= range.getStart();
    }
}
