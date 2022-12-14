package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Utils {
    public static List<String> readAllLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();

        final BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        reader.close();

        return lines;
    }

    public static String readString(String filename) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(filename));
        var line = reader.readLine();

        reader.close();

        return line;
    }

    public static Stream<String> readFileStream(String filename) throws FileNotFoundException {
        final BufferedReader reader = new BufferedReader(new FileReader(filename));

        return reader.lines();
    }

    public static int sign(int x) {
        if (x == 0) {
            return x;
        } else if (x > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
