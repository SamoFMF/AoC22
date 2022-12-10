package day06;

import utils.Utils;

import java.io.IOException;

public class Day06 {

    public static void main(String[] args) throws IOException {
        var data = Utils.readString("inputs/input06.txt");

        part1(data);
        part2(data);
    }

    static void part1(String data) {
        System.out.println(findUniqueSequence(data, 4));
    }

    static void part2(String data) {
        System.out.println(findUniqueSequence(data, 14));
    }

    static int findUniqueSequence(String data, int length) {
        for (int i = length; i < data.length(); ) {
            var result = backSearch(data, i, length);
            if (result.isDuplicate()) {
                i += result.increment();
            } else {
                return i;
            }
        }
        return -1;
    }

    static SearchResult backSearch(String data, int iEnd, int length) {
        var chars = new char[length];
        data.getChars(iEnd - length, iEnd, chars, 0);

        return searchArray(chars);
    }

    static SearchResult searchArray(char[] chars) {
        var seen = new boolean[26];
        for (int i = chars.length - 1; i >= 0; i--) {
            if (seen[chars[i] - 'a']) {
                return new SearchResult(true, i + 1);
            } else {
                seen[chars[i] - 'a'] = true;
            }
        }
        return new SearchResult(false, -1);
    }
}