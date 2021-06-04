package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public class LineSourcePositionChanged extends DomainEvent {
    private final String boardId;
    private final String figureId;
    private final Coordinate sourcePosition;

    public LineSourcePositionChanged(String boardId, String figureId, Coordinate sourcePosition) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.sourcePosition = sourcePosition;
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

}
