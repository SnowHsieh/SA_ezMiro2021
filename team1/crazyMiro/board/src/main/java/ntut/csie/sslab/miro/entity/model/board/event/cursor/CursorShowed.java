package ntut.csie.sslab.miro.entity.model.board.event.cursor;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public class CursorShowed extends DomainEvent {
    private String boardId;
    private String userId;
    private Coordinate position;

    public CursorShowed(String boardId, String userId, Coordinate position) {
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