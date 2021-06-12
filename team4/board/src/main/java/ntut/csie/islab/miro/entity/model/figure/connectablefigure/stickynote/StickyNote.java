package ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote;


import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.event.*;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ConnectableFigure;
import ntut.csie.islab.miro.entity.model.figure.line.ArrowKindEnum;


import java.util.List;
import java.util.UUID;

public class StickyNote extends ConnectableFigure {


    public StickyNote(UUID boardId, Position position, String content, double width, double height, Style style) {
        super(boardId, position, content, width, height, style);
        addDomainEvent(new StickyNoteCreatedDomainEvent(boardId, getFigureId()));
    }

    public StickyNote(UUID boardId, UUID stickyNoteId, Position position, String content, double width, double height, Style style) {
        super(boardId, stickyNoteId, position, content, width, height, style);
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
            Double oldWidth = this.getWidth();
            Double oldHeight = this.getHeight();
            this.setWidth(newWidth);
            this.setHeight(newHeight);
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
    public void attachConnectableFigure(UUID figureId, String attachEndPointKind) {
    }

    @Override
    public void unattachConnectableFigure(String attachEndPointKind) {

    }


    private Boolean isValidSide(double newWidth, double newHeight) {
        return newWidth > 0 && newHeight > 0;
    }

    private Boolean isDifferentSide(double newWidth, double newHeight) {
        return this.getWidth() != newWidth || this.getHeight() != newHeight;
    }
}