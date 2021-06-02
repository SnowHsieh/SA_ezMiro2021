package ntut.csie.team5.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

public class LineMoved extends DomainEvent {

    private final String figureId;
    private final int offsetX;
    private final int offsetY;
    private final String boardId;
    private final FigureType figureType;

    public LineMoved(String figureId, int offsetX, int offsetY, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.boardId = boardId;
        this.figureType = figureType;
    }

    public String igureId() {
        return figureId;
    }

    public int offsetX() {
        return offsetX;
    }

    public int offsetY() {
        return offsetY;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}
