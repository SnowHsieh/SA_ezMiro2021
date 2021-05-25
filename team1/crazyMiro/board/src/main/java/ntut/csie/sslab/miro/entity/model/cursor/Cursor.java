package ntut.csie.sslab.miro.entity.model.cursor;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorDeleted;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public class Cursor extends AggregateRoot<String> {
    private String boardId;
    private String userId;
    private String cursorId;
    private Coordinate position;

    public Cursor(String userId, String boardId, String cursorId) {
        super(cursorId);
        this.boardId = boardId;
        this.cursorId = cursorId;
        this.userId = userId;
        this.position = new Coordinate(0,0);
        addDomainEvent(new CursorCreated(userId, boardId, cursorId));
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void deleteCursor() {
        markAsDelete();
        addDomainEvent(new CursorDeleted(boardId, userId, cursorId));
    }
}
