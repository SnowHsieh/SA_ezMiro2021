package ntut.csie.islab.miro.entity.model.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineDeletedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.LinePathChangedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.TextfigureAttachedByLineDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.event.StickyNoteMovedDomainEvent;


import java.util.List;
import java.util.UUID;

public class Line extends Figure {

    public Line(UUID boardId, List<Position> positionList, int strokeWidth, String color) {
        super(boardId, positionList, strokeWidth, color);
        addDomainEvent(new LineCreatedDomainEvent(boardId, getId()));
    }

    public Line(UUID boardId, UUID lineId, List<Position> positionList, int strokeWidth, String color) {
        super(boardId, lineId, positionList, strokeWidth, color);
        addDomainEvent(new LineCreatedDomainEvent(boardId, getId()));
    }

    @Override
    public void markAsRemoved(UUID boardId, UUID figureId) {
        addDomainEvent(new LineDeletedDomainEvent(boardId, figureId));
    }

    @Override
    public void changeContent(String newContent) {

    }

    @Override
    public void changePosition(Position newPosition) {

    }

    @Override
    public void changeColor(String newColor) {

    }

    @Override
    public void resize(Double newWidth, Double newHeight) {

    }

    @Override
    public FigureTypeEnum getKind() {
        return FigureTypeEnum.LINE;
    }

    @Override
    public void changeLinePath(List<Position> positionList) {
        List<Position> originalPositionList = this.getPositionList();
        this.setPositionList(positionList);
        addDomainEvent(new LinePathChangedDomainEvent(this.getBoardId(), this.getFigureId(), originalPositionList, positionList));

    }

    @Override
    public void attachTextFigure(UUID figureId) {
        List<UUID> attachedTextFigureIdList = this.getAttachedTextFigureIdList();
        attachedTextFigureIdList.add(figureId);
        this.setAttachedTextFigureIdList(attachedTextFigureIdList);
        addDomainEvent(new TextfigureAttachedByLineDomainEvent(this.getBoardId(), this.getFigureId(), attachedTextFigureIdList));
    }
}
