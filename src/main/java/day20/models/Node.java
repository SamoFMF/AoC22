package day20.models;

import utils.Utils;

public class Node {
    private int value;
    private long fullValue;
    private Node prev;
    private Node next;

    public Node(int value) {
        this.value = value;
        fullValue = value;
        prev = null;
        next = null;
    }

    public int getValue() {
        return value;
    }

    public long getFullValue() {
        return fullValue;
    }

    public void updateValue(int key, int mod) {
        fullValue = (long)value * (long)key;
        this.value = Utils.mod(
            Utils.mod(value, mod) * (key % mod),
            mod
        );
    }

    public Node getPrev() {
        return prev;
    }

    public Node getNext() {
        return next;
    }

    public void addAfter(Node node) {
        node.prev = this;
        next = node;
    }

    public void swapNext() {
        var temp = next.next;
        next.prev = prev;
        next.next = this;
        prev.next = next;
        temp.prev = this;
        prev = next;
        next = temp;
    }
}
