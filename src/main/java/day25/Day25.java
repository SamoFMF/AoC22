package day25;

import utils.Utils;

import java.io.FileNotFoundException;

public class Day25 {

    public static void main(String[] args) throws FileNotFoundException {
        Utils.readFileStream("inputs/input25.txt")
            .reduce(Snafu::add)
            .ifPresent(System.out::println);
    }
}
