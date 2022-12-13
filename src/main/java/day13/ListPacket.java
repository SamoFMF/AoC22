package day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static day13.Day13.getPacket;

public class ListPacket implements PacketInterface {
    private final List<PacketInterface> packets;

    public ListPacket(String values) {
        packets = new ArrayList<>();

        var line = values.substring(1, values.length() - 1);
        int level = 0;
        int istart = 0;
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case '[' -> level++;
                case ']' -> level--;
                case ',' -> {
                    if (level == 0) {
                        packets.add(getPacket(line.substring(istart, i)));
                        istart = i + 1;
                    }
                }
            }
        }

        if (line.length() > 0) {
            packets.add(getPacket(line.substring(istart)));
        }
    }

    @Override
    public Optional<Boolean> compareAsLeft(IntPacket packet) {
        return compareAsLeft(packet.getAsList());
    }

    @Override
    public Optional<Boolean> compareAsLeft(ListPacket packet) {
        var len = Math.min(packets.size(), packet.packets.size());

        for (int i = 0; i < len; i++) {
            var result = packets.get(i).compareAsLeft(packet.packets.get(i));

            if (result.isPresent()) {
                return result;
            }
        }

        if (packets.size() == packet.packets.size()) {
            return Optional.empty();
        }

        return Optional.of(packets.size() < packet.packets.size());
    }
}
