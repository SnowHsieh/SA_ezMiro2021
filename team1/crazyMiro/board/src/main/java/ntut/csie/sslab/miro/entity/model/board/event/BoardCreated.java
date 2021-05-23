package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class BoardCreated extends DomainEvent {
    private final String boardId;

    public BoardCreated(String boardId) {
        super(DateProvider.now());
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
