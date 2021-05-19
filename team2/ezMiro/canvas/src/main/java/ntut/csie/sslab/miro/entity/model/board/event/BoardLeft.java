package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardLeft extends DomainEvent {
    private final String boardId;
    private final String userId;

    public BoardLeft(String boardId, String userId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
    }

    public String boardId() {
        return boardId;
    }

    public String userId() {
        return userId;
    }
}