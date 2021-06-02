package ntut.csie.sslab.miro.entity.model.line;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.line.event.LineCreated;
import ntut.csie.sslab.miro.entity.model.line.event.LineDeleted;

public class Line extends AggregateRoot<String> {

    private String boardId;
    private String lineId;
    private String sourceId;
    private String targetId;
    private Coordinate sourcePosition;
    private Coordinate targetPosition;

    public Line(String boardId, String lineId, String sourceId, String targetId, Coordinate sourcePosition, Coordinate targetPosition) {
        super(lineId);
        this.boardId = boardId;
        this.lineId = lineId;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
        addDomainEvent(new LineCreated(boardId, lineId, sourceId, targetId, sourcePosition, targetPosition));
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
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
    }

    public Coordinate getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Coordinate targetPosition) {
        this.targetPosition = targetPosition;
    }

    public void deleteLine() {
        addDomainEvent(new LineDeleted(lineId, boardId));
    }
}
