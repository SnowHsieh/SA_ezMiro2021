package ntut.csie.islab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class BoardContentMightExpire extends DomainEvent {
    private UUID boardId;
    public BoardContentMightExpire(UUID boardId) {
        super(DateProvider.now());
        this.boardId = boardId;
    }

    public UUID getBoardId() {
        return boardId;
    }
}
