package ntut.csie.sslab.miro.entity.model.figure;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;

public abstract class Figure extends AggregateRoot<String> {
    private String boardId;
    private Coordinate coordinate;
    private String color;
    private double width;
    private double height;

    public Figure(String figureId, String boardId, Coordinate coordinate, String color, double width, double height) {
        super(figureId);
        this.boardId = boardId;
        this.coordinate = coordinate;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
