package day22;

import java.util.Arrays;

public class Row {
    private final char[] row;
    private final int rowNum;
    private final int iStart;
    private final int iEnd;

    public Row(String line, int rowNum) {
        this.rowNum = rowNum;


        int i = 0;
        while (line.charAt(i) == ' ') {
            i++;
        }

        iStart = i;
        iEnd = line.length() - 1;
        row = line.substring(i, iEnd + 1).toCharArray();
    }

    public Row(char[] row, int rowNum, int iStart, int iEnd) {
        this.row = row;
        this.rowNum = rowNum;
        this.iStart = iStart;
        this.iEnd = iEnd;
    }

    public char[] getRow() {
        return row;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getStart() {
        return iStart;
    }

    public int getEnd() {
        return iEnd;
    }

    public char getAt(int col) {
        return row[col - iStart];
    }

    public boolean withinLimits(int col) {
        return iStart <= col && col <= iEnd;
    }

    public Row[] splitInHalf() {
        return new Row[]{
            new Row(Arrays.copyOfRange(row, 0, row.length / 2), rowNum, iStart, iStart + 49),
            new Row(Arrays.copyOfRange(row, row.length / 2, row.length), rowNum, iStart + 50, iEnd)
        };
    }
}
