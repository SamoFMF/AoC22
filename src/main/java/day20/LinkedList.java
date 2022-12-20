package day20;

import day20.models.Node;
import utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LinkedList {
    private final Map<Integer, Node> valueToNode;
    private final Node[] nodes;

    private int size;
    private Node start;
    private Node current;

    public LinkedList(int size) {
        valueToNode = new HashMap<>();
        nodes = new Node[size];
        start = null;
        current = null;
    }

    public int size() {
        return size;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public void init(Node node) {
        node.addAfter(node);
        start = node;
        current = node;
        updateList(node);
    }

    public void completeCycle() {
        current.addAfter(start);
    }

    public void setStart(Node node) {
        start = node;
    }

    public void add(Node node) {
        current.addAfter(node);
        current = node;
        updateList(node);
    }

    public Node get(int value) {
        return valueToNode.get(value);
    }

    public long getSumByIndexes(int[] indexes) {
        var iSet = Arrays.stream(indexes)
            .mapToObj(i -> Utils.mod(i, size))
            .collect(Collectors.toSet());

        long sum = 0;
        var cur = start;
        for (int i = 0; i < size; i++) {
            if (iSet.contains(i)) {
                sum += cur.getFullValue();
            }

            cur = cur.getNext();
        }

        return sum;
    }

    public void move(Node node) {
        var n = size - 1;
        int steps = Utils.mod(node.getValue(), n);
        boolean goLeft = false;
        if (steps > n / 2) {
            goLeft = true;
            steps = n - steps;
        }

        for (int i = 0; i < steps; i++) {
            if (goLeft) {
                node.getPrev().swapNext();
            } else {
                node.swapNext();
            }
        }
        current = node;
    }

    private void updateList(Node node) {
        valueToNode.put(node.getValue(), node);
        nodes[size] = node;
        size++;
    }
}
