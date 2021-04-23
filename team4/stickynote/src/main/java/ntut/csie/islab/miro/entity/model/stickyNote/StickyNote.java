package ntut.csie.islab.miro.entity.model.stickyNote;

import ntut.csie.islab.miro.entity.model.stickyNote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.stickyNote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.islab.miro.figure.entity.model.figure.Style;
import ntut.csie.islab.miro.figure.entity.model.figure.Position;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import java.util.UUID;

public class StickyNote extends Figure {
    public StickyNote(UUID boardId, Position position, String content, Style style) {
        super(boardId,position,content,style);
        addDomainEvent(new StickyNoteCreatedDomainEvent(boardId, getFigureId()));
    }
    @Override
    public void markAsRemoved(UUID boardId, UUID figureId) {
        addDomainEvent(new StickyNoteDeleteDomainEvent(boardId, figureId));
    }


}
