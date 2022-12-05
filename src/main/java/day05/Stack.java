package day05;

import java.util.LinkedList;

public class Stack extends LinkedList<String> {
    @Override
    public String pop() {
        return super.removeLast();
    }
}
