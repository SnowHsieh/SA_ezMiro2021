package ntut.csie.islab.miro.entity.model.board.event;

import ntut.csie.islab.miro.entity.model.board.BoardSessionId;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class BoardEnteredDomainEvent extends DomainEvent {
    private UUID boardId;
    private UUID userId;
    private BoardSessionId boardSessionId;

    public BoardEnteredDomainEvent(UUID boardId, UUID userId, BoardSessionId boardSessionId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
        this.boardSessionId = boardSessionId;
    }

    public UUID boardId() {
        return boardId;
    }

    public UUID userId() {
        return userId;
    }

    public BoardSessionId boardSessionId() {
        return boardSessionId;
    }
}
