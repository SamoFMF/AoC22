package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day10 {

    public static void main(String[] args) throws IOException {
        var instructions = Files.readAllLines(Paths.get("inputs/input10.txt"));

        var cpu = new Cpu();
        instructions.forEach(cpu::executeInstruction);

        part1(cpu);
        part2(cpu);
    }

    static void part1(Cpu cpu) {
        System.out.println(cpu.getSignal());
    }

    static void part2(Cpu cpu) {
        cpu.printCrt();
    }
}
