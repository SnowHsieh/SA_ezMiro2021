package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.team5.usecase.figure.FigureDto;

import java.awt.*;

public class NoteDto extends FigureDto {

    private int leftTopPositionX;
    private int leftTopPositionY;
    private int height;
    private int width;
    private String color;
    private String figureType;
    private String text;

    public int getLeftTopPositionX() {
        return leftTopPositionX;
    }

    public int getLeftTopPositionY() {
        return leftTopPositionY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setLeftTopPosition(Point leftTopPosition) {
        this.leftTopPositionX = leftTopPosition.x;
        this.leftTopPositionY = leftTopPosition.y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
