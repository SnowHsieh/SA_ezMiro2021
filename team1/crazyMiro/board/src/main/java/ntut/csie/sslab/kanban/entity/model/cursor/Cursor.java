package ntut.csie.sslab.kanban.entity.model.cursor;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorDeleted;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.kanban.entity.model.Coordinate;

public class Cursor extends AggregateRoot<String> {
    private String boardId;
    private String ip;
    private String cursorId;
    private String sessionId;
    private Coordinate position;

    public Cursor(String boardId, String cursorId, String ip, String sessionId) {
        super(cursorId);
        this.boardId = boardId;
        this.cursorId = cursorId;
        this.ip = ip;
        this.position = new Coordinate(0,0);
        this.sessionId = sessionId;
        addDomainEvent(new CursorCreated(boardId, cursorId, ip, sessionId));
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCursorId() {
        return cursorId;
    }

    public void setCursorId(String cursorId) {
        this.cursorId = cursorId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
        addDomainEvent(new CursorMoved(cursorId, position));
    }

    public void deleteCursor() {
        markAsDelete();
        addDomainEvent(new CursorDeleted(cursorId));
    }
}
