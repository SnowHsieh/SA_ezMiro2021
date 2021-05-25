package ntut.csie.team5.entity.model.figure;

import java.awt.*;

public abstract class ConnectableFigure extends Figure {

//    private Point leftTopPosition;
    private int leftTopPositionX;
    private int leftTopPositionY;
    private int height;
    private int width;

    public ConnectableFigure(String figureId, String boardId, int leftTopPositionX, int leftTopPositionY, int height, int width, FigureType figureType) {
        super(figureId, boardId, figureType);
//        this.leftTopPosition = leftTopPosition;
        this.leftTopPositionX = leftTopPositionX;
        this.leftTopPositionY = leftTopPositionY;
        this.height = height;
        this.width = width;
    }

//    public Point getLeftTopPosition() {
//        return leftTopPosition;
//    }
//
//    public void setLeftTopPosition(Point leftTopPosition) {
//        this.leftTopPosition = leftTopPosition;
//    }

    public int getLeftTopPositionX() {
        return leftTopPositionX;
    }

    public void setLeftTopPositionX(int leftTopPositionX) {
        this.leftTopPositionX = leftTopPositionX;
    }

    public int getLeftTopPositionY() {
        return leftTopPositionY;
    }

    public void setLeftTopPositionY(int leftTopPositionY) {
        this.leftTopPositionY = leftTopPositionY;
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
