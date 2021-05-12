package ntut.csie.sslab.kanban.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.Coordinate;

public class CursorMoved extends DomainEvent {
    private String cursorId;
    private Coordinate position;
    private String sessionId;

    public CursorMoved(String cursorId, Coordinate position, String sessionId) {
        super(DateProvider.now());
        this.cursorId = cursorId;
        this.position = position;
        this.sessionId = sessionId;
    }

    public String getCursorId() {
        return cursorId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public String getSessionId() {
        return sessionId;
    }
}
