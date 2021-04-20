package ntut.csie.sslab.kanban.entity.model.board2.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardMemberRemoved extends DomainEvent {

    private final String userId;
    private final String boardId;

    public BoardMemberRemoved(String userId, String boardId) {
        super(DateProvider.now());
        this.userId = userId;
        this.boardId = boardId;
    }

    public String userId() {
        return userId;
    }

    public String boardId() {
        return boardId;
    }
}
