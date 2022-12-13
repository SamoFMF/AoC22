package day13;

import java.util.Optional;

public class IntPacket implements PacketInterface {

    private final int value;

    public IntPacket(String value) {
        this.value = Integer.parseInt(value);
    }

    public ListPacket getAsList() {
        return new ListPacket("[" + value + "]");
    }

    @Override
    public Optional<Boolean> compareAsLeft(IntPacket packet) {
        if (value == packet.value) {
            return Optional.empty();
        }

        return Optional.of(value < packet.value);
    }

    @Override
    public Optional<Boolean> compareAsLeft(ListPacket packet) {
        return getAsList().compareAsLeft(packet);
    }
}
