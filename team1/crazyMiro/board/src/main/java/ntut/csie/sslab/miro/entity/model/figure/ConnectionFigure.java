package ntut.csie.sslab.miro.entity.model.figure;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.Coordinate;


public abstract class ConnectionFigure extends Figure {

    private String content;
    private int width;
    private int length;
    private Coordinate position;

    public ConnectionFigure(String boardId, String figureId, String color, String content, int width, int length, Coordinate position) {
        super(boardId, figureId, color);
        this.content = content;
        this.width = width;
        this.length = length;
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
