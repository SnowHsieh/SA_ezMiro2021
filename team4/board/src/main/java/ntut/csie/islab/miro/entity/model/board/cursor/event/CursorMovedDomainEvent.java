package ntut.csie.islab.miro.entity.model.board.cursor.event;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;
import java.util.UUID;

public class CursorMovedDomainEvent extends DomainEvent {
    private UUID boardId;
    private UUID userId;
    private final Position originalPosition;
    private final Position newPosition;

    public CursorMovedDomainEvent(UUID boardId, UUID userId, Position originalPosition, Position newPosition) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
        this.originalPosition = originalPosition;
        this.newPosition = newPosition;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Position getOriginalPosition() {
        return originalPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }
}
