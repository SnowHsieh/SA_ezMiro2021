package ntut.csie.islab.miro.entity.model.textFigure.stickynote.event;

import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class StickyNoteMovedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final Position originalPosition;
    private final Position newPosition;

    public StickyNoteMovedDomainEvent(UUID boardId, UUID figureId, Position originalPosition, Position newPosition) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.originalPosition = originalPosition;
        this.newPosition = newPosition;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public Position getOriginalPosition() {
        return originalPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }
}
