package ntut.csie.sslab.miro.entity.model.note;

import java.awt.*;
import java.util.Objects;

public class Coordinate {
    private Point position;

    public Coordinate(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return this.position.getX() == that.getPosition().getX() && this.position.getY() == that.getPosition().getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
