package ntut.csie.team5.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

public class LineEndpointMoved extends DomainEvent {

    private final String figureId;
    private final String endpointId;
    private final int oldPositionX;
    private final int oldPositionY;
    private final int newPositionX;
    private final int newPositionY;
    private final String boardId;
    private final FigureType figureType;

    public LineEndpointMoved(String figureId, String endpointId, int oldPositionX, int oldPositionY, int newPositionX, int newPositionY, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.endpointId = endpointId;
        this.oldPositionX = oldPositionX;
        this.oldPositionY = oldPositionY;
        this.newPositionX = newPositionX;
        this.newPositionY = newPositionY;
        this.boardId = boardId;
        this.figureType = figureType;
    }

    public String figureId() {
        return figureId;
    }

    public String endpointId() {
        return endpointId;
    }

    public int oldPositionX() {
        return oldPositionX;
    }

    public int oldPositionY() {
        return oldPositionY;
    }

    public int newPositionX() {
        return newPositionX;
    }

    public int newPositionY() {
        return newPositionY;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}
