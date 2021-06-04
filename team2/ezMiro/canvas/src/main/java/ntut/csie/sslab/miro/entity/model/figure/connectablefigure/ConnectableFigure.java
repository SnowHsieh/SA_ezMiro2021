package ntut.csie.sslab.miro.entity.model.figure.connectablefigure;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;

public abstract class ConnectableFigure extends Figure {
    private Coordinate coordinate;
    private String color;
    private double width;
    private double height;

    public ConnectableFigure(String figureId, String boardId, Coordinate coordinate, String color, double width, double height) {
        super(figureId, boardId);
        this.coordinate = coordinate;
        this.color = color;
        this.width = width;
        this.height = height;
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