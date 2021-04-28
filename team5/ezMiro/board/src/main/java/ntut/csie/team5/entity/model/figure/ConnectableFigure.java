package ntut.csie.team5.entity.model.figure;

import java.awt.*;

public abstract class ConnectableFigure extends Figure {

    private Point leftTopPosition;
    private int height;
    private int width;

    public ConnectableFigure(String figureId, String boardId, Point leftTopPosition, int height, int width, FigureType figureType) {
        super(figureId, boardId, figureType);
        this.leftTopPosition = leftTopPosition;
        this.height = height;
        this.width = width;
    }

    public Point getLeftTopPosition() {
        return leftTopPosition;
    }

    public void setLeftTopPosition(Point leftTopPosition) {
        this.leftTopPosition = leftTopPosition;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
