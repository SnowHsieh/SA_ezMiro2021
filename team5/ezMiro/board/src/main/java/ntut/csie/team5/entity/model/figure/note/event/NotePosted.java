package ntut.csie.team5.entity.model.figure.note.event;

import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.event.FigureCreated;

public class NotePosted extends FigureCreated {

//    private final Point leftTopPosition;
    private final int leftTopPositionX;
    private final int leftTopPositionY;
    private final int height;
    private final int width;
    private final String color;

    public NotePosted(String figureId, String boardId, int leftTopPositionX, int leftTopPositionY, int height, int width, String color, FigureType figureType) {
        super(figureId, boardId, figureType);
//        this.leftTopPosition = leftTopPosition;
        this.leftTopPositionX = leftTopPositionX;
        this.leftTopPositionY = leftTopPositionY;
        this.height = height;
        this.width = width;
        this.color = color;
    }

//    public Point leftTopPosition() {
//        return leftTopPosition;
//    }

    public int leftTopPositionX() {
        return leftTopPositionX;
    }

    public int leftTopPositionY() {
        return leftTopPositionY;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public String color() {
        return color;
    }
}
