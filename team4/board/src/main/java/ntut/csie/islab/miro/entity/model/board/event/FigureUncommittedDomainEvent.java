package ntut.csie.islab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class FigureUncommittedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID textFigureId;
    public FigureUncommittedDomainEvent(UUID boardId, UUID textFigureId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.textFigureId = textFigureId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getTextFigureId() {
        return textFigureId;
    }
}
