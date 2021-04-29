package ntut.csie.islab.miro.entity.model.figure.stickynote;


import ntut.csie.islab.miro.entity.model.figure.Style;
import ntut.csie.islab.miro.entity.model.figure.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.stickynote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.stickynote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.stickynote.event.StickyNoteEditedDomainEvent;

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
