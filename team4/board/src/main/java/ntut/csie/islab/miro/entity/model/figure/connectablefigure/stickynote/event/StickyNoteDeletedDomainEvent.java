package ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class StickyNoteDeletedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;

    public StickyNoteDeletedDomainEvent(UUID boardId, UUID figureId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }
}
