package ntut.csie.team5.entity.model.figure.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

import java.awt.*;

public class NoteMoved extends DomainEvent {

    private final String figureId;
    private final Point oldPosition;
    private final Point newPosition;
    private final String boardId;
    private final FigureType figureType;

    public NoteMoved(String figureId, Point oldPosition, Point newPosition, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.figureType = figureType;
    }

    public String figureId() {
        return figureId;
    }

    public Point oldPosition() {
        return oldPosition;
    }

    public Point newPosition() {
        return newPosition;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}