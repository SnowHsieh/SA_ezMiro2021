package ntut.csie.sslab.kanban.entity.model.cursor;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CusorCreated;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

public class Cursor extends AggregateRoot<String> {
    private String boardId;
    private String ip;
    private String cursorId;
    private Coordinate position;

    public Cursor(String boardId, String cursorId, String ip) {
        super(cursorId);
        this.boardId = boardId;
        this.cursorId = cursorId;
        this.ip = ip;
        this.position = new Coordinate(0,0);
        addDomainEvent(new CusorCreated(boardId, cursorId, ip));
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

    public void setPosition(Coordinate position) {
        this.position = position;
        addDomainEvent(new CursorMoved(cursorId, position));
    }
}
