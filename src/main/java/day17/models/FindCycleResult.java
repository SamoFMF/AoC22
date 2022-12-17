package day17.models;

import java.util.Map;

public record FindCycleResult(
    Map<String, Repetition> uniqueRepetitions,
    int repeatsFrom
) {
}
