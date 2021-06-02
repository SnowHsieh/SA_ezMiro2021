package ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote;


import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.event.*;
import ntut.csie.islab.miro.entity.model.figure.textfigure.Style;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.textfigure.TextFigure;


import java.util.List;
import java.util.UUID;

public class StickyNote extends TextFigure {

    public StickyNote(UUID boardId, Position position, String content, Style style) {
        super(boardId, position, content, style);

        addDomainEvent(new StickyNoteCreatedDomainEvent(boardId, getFigureId()));
    }

    public StickyNote(UUID boardId,UUID stickyNoteId, Position position, String content, Style style) {
        super(boardId,stickyNoteId, position, content, style);

        addDomainEvent(new StickyNoteCreatedDomainEvent(boardId, stickyNoteId));
    }

    @Override
    public void markAsRemoved(UUID boardId, UUID figureId) {
        addDomainEvent(new StickyNoteDeletedDomainEvent(boardId, figureId));
    }

    @Override
    public void changeContent(String newContent) {
        if (!newContent.isEmpty() && !this.getContent().equals(newContent)) {
            String originalContent = this.getContent();
            this.setContent(newContent);
            addDomainEvent(new StickyNoteContentChangedDomainEvent(this.getBoardId(), this.getFigureId(), originalContent, newContent));
        }
    }

    @Override
    public void changePosition(Position newPosition) {
        Position originalPosition = this.getPosition();
        this.setPosition(newPosition);
        addDomainEvent(new StickyNoteMovedDomainEvent(this.getBoardId(), this.getFigureId(), originalPosition, newPosition));

    }

    @Override
    public void changeColor(String newColor) {
        if (!newColor.isEmpty() && !this.getStyle().getColor().equals(newColor)) {
            String originalColor = this.getStyle().getColor();
            this.getStyle().setColor(newColor);
            addDomainEvent(new StickyNoteColorChangedDomainEvent(this.getBoardId(), this.getFigureId(), originalColor, newColor));
        }
    }

    @Override
    public void resize(Double newWidth, Double newHeight) {
        if (isValidSide(newWidth, newHeight) && isDifferentSide(newWidth, newHeight)) {
            Double oldWidth = this.getStyle().getWidth();
            Double oldHeight = this.getStyle().getHeight();
            this.getStyle().setWidth(newWidth);
            this.getStyle().setHeight(newHeight);
            addDomainEvent(new StickyNoteResizedDomainEvent(this.getBoardId(), this.getFigureId(), oldWidth, oldHeight, newWidth, newHeight));
        }
    }

    @Override
    public FigureTypeEnum getKind() {
        return FigureTypeEnum.STICKYNOTE;
    }

    @Override
    public void changeLinePath(List<Position> positionList) {

    }

    @Override
    public void attachTextFigure(UUID figureId) {

    }

    private Boolean isValidSide(double newWidth,double newHeight){
        return newWidth > 0 && newHeight > 0;
    }
    private Boolean isDifferentSide(double newWidth,double newHeight){
        return this.getStyle().getWidth() != newWidth || this.getStyle().getHeight() != newHeight;
    }
}
