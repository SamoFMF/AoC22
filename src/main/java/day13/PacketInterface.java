package day13;

import java.util.Optional;

public interface PacketInterface {
    Optional<Boolean> compareAsLeft(IntPacket packet);
    Optional<Boolean> compareAsLeft(ListPacket packet);

    default Optional<Boolean> compareAsLeft(PacketInterface packet) {
        if (packet instanceof IntPacket) {
            return compareAsLeft((IntPacket) packet);
        } else {
            return compareAsLeft((ListPacket) packet);
        }
    }
}
