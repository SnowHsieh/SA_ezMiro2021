package ntut.csie.sslab.miro.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

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
