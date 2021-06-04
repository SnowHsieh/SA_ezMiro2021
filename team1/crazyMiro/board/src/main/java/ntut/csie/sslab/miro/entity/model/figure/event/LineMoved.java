package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

import java.util.Date;

public class LineMoved extends DomainEvent {
    private final String boardId;
    private final String figureId;
    private final Coordinate sourcePosition;
    private final Coordinate targetPosition;

    public LineMoved(String boardId, String figureId, Coordinate sourcePosition, Coordinate targetPosition) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getFigureId() {
        return figureId;
    }

    public Coordinate getSourcePosition() {
        return sourcePosition;
    }

    public Coordinate getTargetPosition() {
        return targetPosition;
    }
}
