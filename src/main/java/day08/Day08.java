package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day08 {

    public static void main(String[] args) throws IOException {
        var start = System.currentTimeMillis();
        var forest = Files.readAllLines(Paths.get("inputs/input08.txt"))
            .stream()
            .map(line -> line
                .chars()
                .mapToObj(Tree::new)
                .toArray(Tree[]::new)
            ).toArray(Tree[][]::new);

        part1(forest);
        part2(forest);
        System.out.println(System.currentTimeMillis() - start);
    }

    static void part1(Tree[][] forest) {
        var n = forest.length;

        for (int i = 0; i < n; i++) {
            solveForTreeLine(forest, i, 0, 0, 1); // Right
            solveForTreeLine(forest, i, n - 1, 0, -1); // Left
            solveForTreeLine(forest, 0, i, 1, 0); // Down
            solveForTreeLine(forest, n - 1, i, -1, 0); // Up
        }

        var result = 0;
        for (var trees : forest) {
            for (var tree : trees) {
                if (tree.isVisible()) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }

    static void part2(Tree[][] forest) {
        var n = forest.length;

        int maxScenicScore = 0;
        for (int row = 1; row < n - 1; row++) {
            for (int col = 1; col < n - 1; col++) {
                maxScenicScore = Math.max(
                    maxScenicScore,
                    getScenicScore(forest, row, col, forest[row][col].getHeight())
                );
            }
        }

        System.out.println(maxScenicScore);
    }

    static void solveForTreeLine(Tree[][] forest, int row, int col, int drow, int dcol) {
        int maxHeight = -1;
        while (row >= 0 && row < forest.length && col >= 0 && col < forest.length) {
            maxHeight = forest[row][col].checkVisibility(maxHeight);
            row += drow;
            col += dcol;
        }
    }

    static int getScenicScore(Tree[][] forest, int rowStart, int colStart, int height) {
        var n = forest.length;

        var scenicScore = checkDirection(forest, n, height, rowStart, colStart, 1, 0); // Down
        scenicScore *= checkDirection(forest, n, height, rowStart, colStart, -1, 0); // Up
        scenicScore *= checkDirection(forest, n, height, rowStart, colStart, 0, 1); // Right
        scenicScore *= checkDirection(forest, n, height, rowStart, colStart, 0, -1); // Left

        return scenicScore;
    }

    static int checkDirection(Tree[][] forest, int n, int height, int row, int col, int drow, int dcol) {
        int numTrees = 0;
        while (row > 0 && col > 0 && row < n - 1 && col < n - 1) {
            row += drow;
            col += dcol;
            numTrees++;
            var treeHeight = forest[row][col].getHeight();
            if (height <= treeHeight) break;
        }
        return numTrees;
    }
}
