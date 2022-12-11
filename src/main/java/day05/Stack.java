package day05;

import java.util.ArrayList;
import java.util.List;

public class Stack extends ArrayList<Character> {

    public Character getLast() {
        return get(size() - 1);
    }

    public Character pop() {
        return remove(size() - 1);
    }

    public List<Character> removeLastItems(int quantity) {
        int idx = size() - quantity;
        var toRemove = subList(idx, size());
        var toReturn = new ArrayList<>(toRemove);
        toRemove.clear();
        return toReturn;
    }
}
