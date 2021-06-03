package ntut.csie.sslab.miro.entity.model.cursor;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorEvents;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import java.util.UUID;

public class Cursor extends AggregateRoot<String> {
    private String boardId;
    private String userId;
    private Coordinate coordinate;

    public Cursor(String cursorId, String boardId, String userId, Coordinate coordinate) {
        super(cursorId);
        this.boardId = boardId;
        this.userId = userId;
        this.coordinate = coordinate;
        addDomainEvent(new CursorEvents.CursorCreated(UUID.randomUUID(), cursorId, userId, coordinate, boardId, DateProvider.now()));
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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void move(Coordinate coordinate) {
        setCoordinate(coordinate);
        addDomainEvent(new CursorEvents.CursorMoved(UUID.randomUUID(), getId(), userId, coordinate, boardId, DateProvider.now()));
    }

    public void markAsRemoved() {
        addDomainEvent(new CursorEvents.CursorRemoved(UUID.randomUUID(), getId(), boardId, DateProvider.now()));
    }
}