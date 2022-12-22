package day22;

import utils.Position;

public class Me {
    private Position position;
    private Position direction;

    public Me(Position position, Position direction) {
        this.position = position;
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public Position getDirection() {
        return direction;
    }

    public Position getNext() {
        return position.addDelta(direction.x(), direction.y());
    }

    public void turnRight() {
        direction = new Position(-direction.y(), direction.x());
    }

    public void turnLeft() {
        direction = new Position(direction.y(), -direction.x());
    }

    public void moveTo(Position position) {
        this.position = position;
    }

    public void update(Position position, Position direction) {
        this.position = position;
        this.direction = direction;
    }
}
