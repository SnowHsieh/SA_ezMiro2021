package ntut.csie.islab.miro.entity.model.textFigure.stickynote.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;
import java.util.UUID;

public class StickyNoteResizedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final Double oldWidth;
    private final Double oldHeight;
    private final Double newWidth;
    private final Double newHeight;

    public StickyNoteResizedDomainEvent(UUID boardId, UUID figureId, Double oldWidth, Double oldHeight, Double newWidth, Double newHeight) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public Double getOldWidth() {
        return oldWidth;
    }

    public Double getOldHeight() {
        return oldHeight;
    }

    public Double getNewWidth() {
        return newWidth;
    }

    public Double getNewHeight() {
        return newHeight;
    }
}
