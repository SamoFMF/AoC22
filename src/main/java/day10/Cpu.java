package day10;

public class Cpu {
    private int X;
    private int cycle;
    private int signal;

    private final StringBuilder crt;

    public Cpu() {
        X = 1;
        cycle = 0;
        signal = 0;

        crt = new StringBuilder();
    }

    public int getSignal() {
        return signal;
    }

    public void printCrt() {
        System.out.println(crt);
    }

    public void executeInstruction(String line) {
        doCycle();
        if (!"noop".equals(line)) {
            doCycle();
            X += Integer.parseInt(line.split(" ")[1]);
        }
    }

    private void doCycle() {
        cycle++;
        updateSignal();
        updateCrt();
    }

    private void updateSignal() {
        if ((cycle - 20) % 40 == 0) {
            signal += cycle * X;
        }
    }

    private void updateCrt() {
        var position = (cycle - 1) % 40;

        if (position == 0) {
            crt.append('\n');
        }

        if (position >= X - 1 && position <= X + 1) {
            crt.append('#');
        } else {
            crt.append('.');
        }
    }
}
