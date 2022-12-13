package day13;

import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input13.txt");

        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            var pair = new Pair();
            pair.addLeftPacket(lines.get(i));
            pair.addRightPacket(lines.get(i + 1));
            pairs.add(pair);
        }

        part1(pairs);
        part2(pairs);
    }

    static void part1(List<Pair> pairs) {
        int sum = 0;
        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).isCorrect()) {
                sum += i + 1;
            }
        }

        System.out.println(sum);
    }

    static void part2(List<Pair> pairs) {
        int smaller1 = 1;
        int smaller2 = 1;
        var packet1 = getPacket("[[2]]");
        var packet2 = getPacket("[[6]]");
        for (var pair : pairs) {
            if (pair.getLeft().compareAsLeft(packet1).orElse(false)) smaller1++;
            if (pair.getLeft().compareAsLeft(packet2).orElse(false)) smaller2++;
            if (pair.getRight().compareAsLeft(packet1).orElse(false)) smaller1++;
            if (pair.getRight().compareAsLeft(packet2).orElse(false)) smaller2++;
        }

        if (packet1.compareAsLeft(packet2).orElse(false)) {
            smaller2++;
        } else {
            smaller1++;
        }

        int result = smaller1 * smaller2;
        System.out.println(result);
    }

    public static PacketInterface getPacket(String packet) {
        if (packet.length() == 0 || packet.charAt(0) == '[') {
            return new ListPacket(packet);
        } else {
            return new IntPacket(packet);
        }
    }
}
