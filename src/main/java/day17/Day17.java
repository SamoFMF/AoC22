package day17;

import day17.models.FindCycleResult;
import day17.models.Repetition;
import day17.rocks.*;
import utils.Position;
import utils.Utils;

import java.io.IOException;
import java.util.*;

public class Day17 {

    public static void main(String[] args) throws IOException {
        var wind = Utils.readString("inputs/input17.txt").toCharArray();

        part1(wind);
        part2(wind);
    }

    static void part1(char[] wind) {
        System.out.println(getHeight(wind, new HashSet<>()));
    }

    static void part2(char[] wind) {
        var cycleResult = findCycle(wind, new HashSet<>());
        var uniqueRepetitions = cycleResult.uniqueRepetitions();
        var start = cycleResult.repeatsFrom();

        long numRocks = 1_000_000_000_000L;
        long cycleLength = uniqueRepetitions.size() - start;
        long cycles = (numRocks - start * 5L) / (cycleLength * 5L);
        long remainder = ((numRocks - start * 5L) % ((uniqueRepetitions.size() - start) * 5L)) / 5;

        var h0 = 0;
        var h1 = 0;
        var h2 = 0;
        for (var val : uniqueRepetitions.values()) {
            if (val.index() == start - 1) {
                h0 = val.height();
            } else if (val.index() == uniqueRepetitions.size() - 1) {
                h1 = val.height();
            } else if (val.index() == remainder + start - 1) {
                h2 = val.height();
            }
        }

        long dh1 = h1 - h0;
        long dh2 = h2 - h0;
        long result = (long) h0
            + cycles * dh1
            + dh2;

        System.out.println(result + 1);
    }

    static int getHeight(
        char[] wind,
        Set<Position> covered
    ) {
        int windIdx = 0;
        int curHeight = -1;
        for (int rockNum = 0; rockNum < 2022; rockNum++) {
            var rock = getRock(curHeight + 4, rockNum, covered);
            var another = false;
            do {
                rock.tryMove(wind[windIdx]);
                another = rock.tryMove('v');
                windIdx = (windIdx + 1) % wind.length;
            } while (another);
            curHeight = Math.max(curHeight, rock.getHeight());
        }

        return curHeight + 1;
    }

    static FindCycleResult findCycle(
        char[] wind,
        Set<Position> covered
    ) {
        Map<String, Repetition> uniqueRepetitions = new HashMap<>();

        int windIdx = 0;
        int curHeight = -1;
        int rockNum = 0;
        StringBuilder builder = new StringBuilder();
        while (true) {
            builder.append('|');
            var rock = getRock(curHeight + 4, rockNum, covered);
            var another = false;
            do {
                builder.append(wind[windIdx]);
                rock.tryMove(wind[windIdx]);
                another = rock.tryMove('v');
                windIdx = (windIdx + 1) % wind.length;
            } while (another);

            curHeight = Math.max(curHeight, rock.getHeight());
            if (rockNum == 4) {
                var windRotation = builder.toString();
                if (uniqueRepetitions.containsKey(windRotation)) {
                    return new FindCycleResult(
                        uniqueRepetitions,
                        uniqueRepetitions.get(windRotation).index()
                    );
                } else {
                    uniqueRepetitions.put(windRotation, new Repetition(uniqueRepetitions.size(), curHeight));
                }
                builder = new StringBuilder();
                rockNum = 0;
            } else {
                rockNum++;
            }
        }
    }

    static AbstractRock getRock(int y, int i, Set<Position> covered) {
        return switch (i % 5) {
            case 0 -> new Rock0(y, covered);
            case 1 -> new Rock1(y, covered);
            case 2 -> new Rock2(y, covered);
            case 3 -> new Rock3(y, covered);
            default -> new Rock4(y, covered);
        };
    }
}
