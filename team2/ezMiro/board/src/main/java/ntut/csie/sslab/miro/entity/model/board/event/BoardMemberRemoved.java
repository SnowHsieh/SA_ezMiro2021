package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
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
