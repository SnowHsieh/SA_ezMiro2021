package ntut.csie.team5.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CursorMoved extends DomainEvent {

    private final String boardId;
    private final String boardSessionId;
    private final String userId;
    private final int positionX;
    private final int positionY;

    public CursorMoved(String boardId, String boardSessionId, String userId, int positionX, int positionY) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.boardSessionId = boardSessionId;
        this.userId = userId;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String boardId() {
        return boardId;
    }

    public String boardSessionId() {
        return boardSessionId;
    }

    public int positionX() {
        return positionX;
    }

    public int positionY() {
        return positionY;
    }
}
