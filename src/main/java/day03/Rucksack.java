package day03;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Rucksack {
    private final List<Set<Integer>> compartments;
    private final Set<Integer> everything;

    public Rucksack(String line) {
        var mid = line.length() / 2;
        compartments = Arrays.stream(new String[]{line.substring(0, mid), line.substring(mid)})
            .map(compartment -> compartment.chars().boxed().collect(Collectors.toSet()))
            .toList();

        everything = line.chars().boxed().collect(Collectors.toSet());
    }

    public Set<Integer> getEverything() {
        return everything;
    }

    public int getRepeated() {
        compartments.get(0).retainAll(compartments.get(1));

        return compartments.get(0).iterator().next();
    }

    public int getCommon(Set<Integer> rucksack1, Set<Integer> rucksack2) {
        everything.retainAll(rucksack1);
        everything.retainAll(rucksack2);

        return everything.iterator().next();
    }
}
