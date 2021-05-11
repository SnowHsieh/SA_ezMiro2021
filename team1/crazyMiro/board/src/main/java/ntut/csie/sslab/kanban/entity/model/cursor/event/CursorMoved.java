package ntut.csie.sslab.kanban.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

import java.util.Date;

public class CursorMoved extends DomainEvent {
    private String cursorId;
    private Coordinate position;

    public CursorMoved(String cursorId, Coordinate position) {
        super(DateProvider.now());
        this.cursorId = cursorId;
        this.position = position;
    }

    public String getCursorId() {
        return cursorId;
    }

    public Coordinate getPosition() {
        return position;
    }
}
