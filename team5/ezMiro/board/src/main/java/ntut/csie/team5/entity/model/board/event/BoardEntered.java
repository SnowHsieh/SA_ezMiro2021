package ntut.csie.team5.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardEntered extends DomainEvent {

    private final String boardId;
    private final String boardSessionId;
    private final String userId;

    public BoardEntered(String boardId, String boardSessionId, String userId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.boardSessionId = boardSessionId;
        this.userId = userId;
    }

    public String boardId() {
        return boardId;
    }

    public String boardSessionId() {
        return boardSessionId;
    }

    public String userId() {
        return userId;
    }
}
