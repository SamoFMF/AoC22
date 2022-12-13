package day13;

import static day13.Day13.getPacket;

public class Pair {
    private PacketInterface left;
    private PacketInterface right;

    public PacketInterface getLeft() {
        return left;
    }

    public PacketInterface getRight() {
        return right;
    }

    public void addLeftPacket(String line) {
        left = getPacket(line);
    }

    public void addRightPacket(String line) {
        right = getPacket(line);
    }

    public boolean isCorrect() {
        return left.compareAsLeft(right).orElse(false);
    }
}
