package ntut.csie.islab.miro.entity.model.textFigure.stickynote.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class StickyNoteEditedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final String originalContent;
    private final String newContent;


    public StickyNoteEditedDomainEvent(UUID boardId, UUID figureId, String originalContent, String newContent ) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.originalContent = originalContent;
        this.newContent = newContent;

    }

    public UUID getFigureId() {
        return figureId;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public String getNewContent() {
        return newContent;
    }

    public UUID getBoardId() {
        return boardId;
    }
}
