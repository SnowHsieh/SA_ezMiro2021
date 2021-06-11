package ntut.csie.islab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class FigureUncommittedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID connectableFigureId;
    public FigureUncommittedDomainEvent(UUID boardId, UUID connectableFigureId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.connectableFigureId = connectableFigureId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getConnectableFigureId() {
        return connectableFigureId;
    }
}
