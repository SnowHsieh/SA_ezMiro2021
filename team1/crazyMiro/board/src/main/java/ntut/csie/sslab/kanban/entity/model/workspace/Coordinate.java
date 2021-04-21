package ntut.csie.sslab.kanban.entity.model.workspace;

import ntut.csie.sslab.ddd.model.ValueObject;

public class Coordinate extends ValueObject {

    private long x;
    private long y;

    public Coordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }
}
