package ntut.csie.sslab.miro.entity.model.line.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public class LineCreated extends DomainEvent {

    private final String boardId;
    private final String lineId;
    private final String sourceId;
    private final String targetId;
    private final Coordinate sourcePosition;
    private final Coordinate targetPosition;

    public LineCreated(String boardId, String lineId, String sourceId, String targetId, Coordinate sourcePosition, Coordinate targetPosition) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.lineId = lineId;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getLineId() {
        return lineId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public Coordinate getSourcePosition() {
        return sourcePosition;
    }

    public Coordinate getTargetPosition() {
        return targetPosition;
    }
}
