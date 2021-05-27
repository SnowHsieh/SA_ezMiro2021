package ntut.csie.sslab.miro.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

import java.util.Date;

public class CursorMoved extends DomainEvent {
    private String boardId;
    private String userId;
    private Coordinate position;

    public CursorMoved(String boardId, String userId, Coordinate position) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
        this.position = position;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getUserId() {
        return userId;
    }

    public Coordinate getPosition() {
        return position;
    }
}
