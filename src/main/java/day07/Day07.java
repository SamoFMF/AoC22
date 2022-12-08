package day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day07 {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("inputs/input07.txt"));
        var root = parseInput(lines);
        var size = root.getDirSize();

        part1(root);
        part2(root, size);
    }

    static void part1(Directory root) {
        System.out.println(root.getSumOfSmallDirs());
    }

    static void part2(Directory root, long size) {
        var missingSpace = size - 40_000_000;
        System.out.println(root.findSmallestOverSize(missingSpace));
    }

    static Directory parseInput(List<String> lines) {
        var root = new Directory(null);
        Directory dir = root;

        for (var line : lines) {
            var commands = line.split(" ");
            switch (commands[0]) {
                case "$" -> {
                    if ("cd".equals(commands[1])) {
                        dir = handleChangeDir(commands[2], dir, root);
                    }
                }
                case "dir" -> dir.addChild(commands[1]);
                default -> dir.addFile(commands[1], Long.parseLong(commands[0]));
            }
        }

        return root;
    }

    static Directory handleChangeDir(String name, Directory dir, Directory root) {
        return
            switch (name) {
                case "/" -> root;
                case ".." -> dir.getParent();
                default -> dir.getChild(name);
            };
    }
}
