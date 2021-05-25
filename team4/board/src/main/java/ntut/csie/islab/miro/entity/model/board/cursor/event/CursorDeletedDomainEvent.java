package ntut.csie.islab.miro.entity.model.board.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class CursorDeletedDomainEvent extends DomainEvent {
    private UUID boardId;
    private UUID userId;

    public CursorDeletedDomainEvent(UUID boardId, UUID userId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
    }

    public UUID boardId() {
        return boardId;
    }

    public UUID userId() {
        return userId;
    }
}
