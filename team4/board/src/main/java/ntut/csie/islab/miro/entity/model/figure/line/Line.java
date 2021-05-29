package ntut.csie.islab.miro.entity.model.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineCreatedDomainEvent;


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
}
