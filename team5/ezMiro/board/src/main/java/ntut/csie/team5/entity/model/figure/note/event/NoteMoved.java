package ntut.csie.team5.entity.model.figure.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

public class NoteMoved extends DomainEvent {

    private final String figureId;
//    private final Point oldPosition;
//    private final Point newPosition;
    private final int oldLeftTopPositionX;
    private final int oldLeftTopPositionY;
    private final int newLeftTopPositionX;
    private final int newLeftTopPositionY;
    private final String boardId;
    private final FigureType figureType;

    public NoteMoved(String figureId, int oldLeftTopPositionX, int oldLeftTopPositionY, int newLeftTopPositionX, int newLeftTopPositionY, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
//        this.oldPosition = oldPosition;
//        this.newPosition = newPosition;
        this.oldLeftTopPositionX = oldLeftTopPositionX;
        this.oldLeftTopPositionY = oldLeftTopPositionY;
        this.newLeftTopPositionX = newLeftTopPositionX;
        this.newLeftTopPositionY = newLeftTopPositionY;
        this.figureType = figureType;
    }

    public String figureId() {
        return figureId;
    }

//    public Point oldPosition() {
//        return oldPosition;
//    }
//
//    public Point newPosition() {
//        return newPosition;
//    }

    public int oldLeftTopPositionX() {
        return oldLeftTopPositionX;
    }

    public int oldLeftTopPositionY() {
        return oldLeftTopPositionY;
    }

    public int newLeftTopPositionX() {
        return newLeftTopPositionX;
    }

    public int newLeftTopPositionY() {
        return newLeftTopPositionY;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}