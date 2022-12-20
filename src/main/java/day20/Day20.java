package day20;

import day20.models.Node;
import utils.Utils;

import java.io.IOException;
import java.util.*;

public class Day20 {

    public static void main(String[] args) throws IOException {
        var lines = Utils.readAllLines("inputs/input20.txt");

        part1(lines);
        part2(lines);
    }

    static void part1(List<String> lines) {
        var nodes = createNodes(lines);
        for (var node : nodes.getNodes()) {
            nodes.move(node);
        }

        nodes.setStart(nodes.get(0));
        System.out.println(nodes.getSumByIndexes(new int[]{1000, 2000, 3000}));
    }

    static void part2(List<String> lines) {
        var nodes = createNodes(lines);
        var n = nodes.size() - 1;
        var key = 811589153;

        var order = nodes.getNodes();

        Arrays.stream(order)
            .forEach(node -> node.updateValue(key, n));

        for (int i = 0; i < 10; i++) {
            for (var node : order) {
                nodes.move(node);
            }
        }

        nodes.setStart(nodes.get(0));
        System.out.println(nodes.getSumByIndexes(new int[]{1000, 2000, 3000}));
    }

    static LinkedList createNodes(List<String> lines) {
        LinkedList nodes = new LinkedList(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            var value = Integer.parseInt(lines.get(i));
            var node = new Node(value);

            if (i == 0) {
                nodes.init(node);
            } else {
                nodes.add(node);
            }
        }
        nodes.completeCycle();
        return nodes;
    }
}
