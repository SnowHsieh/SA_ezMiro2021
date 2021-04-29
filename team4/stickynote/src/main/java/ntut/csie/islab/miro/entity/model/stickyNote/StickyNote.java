package ntut.csie.islab.miro.entity.model.stickyNote;

import ntut.csie.islab.miro.entity.model.stickyNote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.stickyNote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.islab.miro.entity.model.stickyNote.event.StickyNoteEditedDomainEvent;
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
    @Override
    public void changeContent(String newContent) {

        if(!newContent.isEmpty() && !this.getContent().equals(newContent)) {
            String originalContent = this.getContent();
            this.setContent(newContent);
            addDomainEvent(new StickyNoteEditedDomainEvent(this.getBoardId(), this.getFigureId(), originalContent, newContent));
        }
    }


}
