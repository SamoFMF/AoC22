package day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cpu {
    private int X;
    private int cycle;
    private int signal;
    private String[] crtLine;

    private final List<String[]> crt;

    public Cpu() {
        X = 1;
        cycle = 0;
        signal = 0;

        crt = new ArrayList<>();
    }

    public int getSignal() {
        return signal;
    }

    public void printCrt() {
        for (var line : crt) {
            System.out.println(String.join("", line));
        }
    }

    public void executeInstruction(String line) {
        cycle++;
        update();
        if (!"noop".equals(line)) {
            cycle++;
            update();
            X += Integer.parseInt(line.split(" ")[1]);
        }
    }

    private void update() {
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
            crtLine = createCrtLine();
            crt.add(crtLine);
        }

        if (position >= X - 1 && position <= X + 1) {
            crtLine[position] = "#";
        }
    }

    private String[] createCrtLine() {
        var line = new String[40];
        Arrays.fill(line, ".");
        return line;
    }
}
