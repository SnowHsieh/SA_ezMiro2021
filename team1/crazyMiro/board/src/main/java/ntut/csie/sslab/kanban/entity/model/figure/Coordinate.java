package ntut.csie.sslab.kanban.entity.model.figure;

import ntut.csie.sslab.ddd.model.ValueObject;

public class Coordinate extends ValueObject {

    private long x;
    private long y;

    public Coordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }


    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public boolean equals(Coordinate coordinate){
        return coordinate.getX() == getX() && coordinate.getY() == getY() ;
    }
}
