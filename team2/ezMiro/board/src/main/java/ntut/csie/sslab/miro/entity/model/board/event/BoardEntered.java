package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardEntered extends DomainEvent {

    private final String userId;
    private final String boardId;
    private final String boardSessionId;

    public BoardEntered(String userId, String boardId, String boardSessionId) {
        super(DateProvider.now());
        this.userId = userId;
        this.boardId = boardId;
        this.boardSessionId = boardSessionId;
    }

    public String userId() {
        return userId;
    }

    public String boardId() {
        return boardId;
    }

    public String boardSessionId() {
        return boardSessionId;
    }
}
