package ntut.csie.sslab.kanban.entity.model.board2.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardLeft extends DomainEvent {

    private final String userId;
    private final String boardId;
    private final String boardSessionId;

    public BoardLeft(String userId, String boardId, String boardSessionId) {
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
