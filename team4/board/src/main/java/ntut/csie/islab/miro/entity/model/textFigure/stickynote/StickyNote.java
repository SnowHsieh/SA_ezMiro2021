package ntut.csie.islab.miro.entity.model.textFigure.stickynote;


import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteEditedDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteMovedDomainEvent;

import java.util.UUID;

public class StickyNote extends TextFigure {
    public StickyNote(UUID boardId, Position position, String content, Style style) {
        super(boardId,position,content,style);

        addDomainEvent(new StickyNoteCreatedDomainEvent(boardId, getFigureId()));
    }
    @Override
    public void markAsRemoved(UUID boardId, UUID figureId) {
        addDomainEvent(new StickyNoteDeleteDomainEvent(boardId, figureId));
    }
    @Override
    public void changeContent(String newContent) { //todo : Should changeContent revised to editFigure
        if(!newContent.isEmpty() && !this.getContent().equals(newContent)) {
            String originalContent = this.getContent();
            this.setContent(newContent);
            addDomainEvent(new StickyNoteEditedDomainEvent(this.getBoardId(), this.getFigureId(), originalContent, newContent));
        }


    }
    @Override
    public void changePosition(Position newPosition) {
        Position originalPosition = this.getPosition();
        this.setPosition(newPosition);
        addDomainEvent(new StickyNoteMovedDomainEvent(this.getBoardId(), this.getFigureId(), originalPosition, newPosition));

    }


}
