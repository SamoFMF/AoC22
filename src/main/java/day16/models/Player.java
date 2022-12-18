package day16.models;

public class Player {
    private final Valve currentValve;
    private int readyIn;

    public Player(Valve valve, int time) {
        currentValve = valve;
        readyIn = time;
    }

    public Valve getValve() {
        return currentValve;
    }

    public int getReadyIn() {
        return readyIn;
    }

    public boolean isReady() {
        return readyIn == 0;
    }

    public void passTime(int time) {
        readyIn -= time;
    }

    public Player getPlayer(Valve valve) {
        return new Player(
            valve,
            currentValve.getDistances().get(valve) + 1
        );
    }

    public Player getCopy() {
        return new Player(
            currentValve,
            readyIn
        );
    }
}
