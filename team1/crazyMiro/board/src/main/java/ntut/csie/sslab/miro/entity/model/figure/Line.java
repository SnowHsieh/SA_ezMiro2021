package ntut.csie.sslab.miro.entity.model.figure;

import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.event.*;

public class Line extends Figure {

    private String sourceId;
    private String targetId;
    private Coordinate sourcePosition;
    private Coordinate targetPosition;

    public Line(String boardId, String lineId, String sourceId, String targetId, Coordinate sourcePosition, Coordinate targetPosition) {
        super(boardId, lineId, "black");
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
        addDomainEvent(new LineCreated(boardId, lineId, sourceId, targetId, sourcePosition, targetPosition));
    }


    public String getLineId() {
        return getFigureId();
    }

    public void setLineId(String lineId) {
        setFigureId(lineId);
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Coordinate getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(Coordinate sourcePosition) {
        this.sourcePosition = sourcePosition;
        addDomainEvent(new LineSourcePositionChanged(getBoardId(), getLineId(), sourcePosition));
    }

    public Coordinate getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Coordinate targetPosition) {
        this.targetPosition = targetPosition;
        addDomainEvent(new LineTargetPositionChanged(getBoardId(), getLineId(), targetPosition));
    }

    public void deleteLine() {
        addDomainEvent(new LineDeleted(getFigureId(), getBoardId()));
    }

    public void setPosition(Coordinate sourcePosition, Coordinate targetPosition) {
        setSourcePosition(sourcePosition);
        setTargetPosition(targetPosition);
        addDomainEvent(new LineMoved(getBoardId(), getFigureId(), sourcePosition, targetPosition));
    }

    @Override
    public FigureType getType() {
        return FigureType.Line;
    }
}
