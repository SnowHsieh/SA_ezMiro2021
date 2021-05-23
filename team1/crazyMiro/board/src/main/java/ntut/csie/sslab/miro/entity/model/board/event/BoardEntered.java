package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

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

    public String getUserId() {
        return userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBoardSessionId() {
        return boardSessionId;
    }
}
