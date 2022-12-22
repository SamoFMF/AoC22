package day22;

import utils.Position;

import java.util.function.Function;

public class CubeLayer {
    private final char[][] layer;

    private int rowStart;
    private int rowEnd;
    private int colStart;
    private int colEnd;

    private Function<Position, UpdateMe> rightFunction;
    private Function<Position, UpdateMe> leftFunction;
    private Function<Position, UpdateMe> upFunction;
    private Function<Position, UpdateMe> downFunction;

    public CubeLayer(int size) {
        layer = new char[size][];

        rowStart = Integer.MAX_VALUE;
        rowEnd = Integer.MIN_VALUE;
        colStart = Integer.MAX_VALUE;
        colEnd = Integer.MIN_VALUE;

    }

    public boolean contains(Position position) {
        return position.x() >= colStart
            && position.x() <= colEnd
            && position.y() >= rowStart
            && position.y() <= rowEnd;
    }

    public char getAt(Position position) {
        return layer[position.y() - rowStart][position.x() - colStart];
    }

    public void addRow(Row row, int i) {
        layer[i] = row.getRow();

        if (row.getRowNum() < rowStart) rowStart = row.getRowNum();
        if (row.getRowNum() > rowEnd) rowEnd = row.getRowNum();
        if (row.getStart() < colStart) colStart = row.getStart();
        if (row.getEnd() > colEnd) colEnd = row.getEnd();
    }

    public UpdateMe getNext(Position next, Position dir) {
        if (next.x() > colEnd) return rightFunction.apply(next);
        else if (next.x() < colStart) return leftFunction.apply(next);
        else if (next.y() > rowEnd) return downFunction.apply(next);
        else if (next.y() < rowStart) return upFunction.apply(next);
        else return new UpdateMe(next, dir);
    }

    public void setRightFunction(Function<Position, UpdateMe> rightFunction) {
        this.rightFunction = rightFunction;
    }

    public void setLeftFunction(Function<Position, UpdateMe> leftFunction) {
        this.leftFunction = leftFunction;
    }

    public void setUpFunction(Function<Position, UpdateMe> upFunction) {
        this.upFunction = upFunction;
    }

    public void setDownFunction(Function<Position, UpdateMe> downFunction) {
        this.downFunction = downFunction;
    }
}
