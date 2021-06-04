package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

import java.util.Date;

public class LineTargetPositionChanged extends DomainEvent {

    private final String boardId;
    private final String figureId;
    private final Coordinate targetPosition;

    public LineTargetPositionChanged(String boardId, String figureId, Coordinate targetPosition) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.targetPosition = targetPosition;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getFigureId() {
        return figureId;
    }

    public Coordinate getTargetPosition() {
        return targetPosition;
    }
}
