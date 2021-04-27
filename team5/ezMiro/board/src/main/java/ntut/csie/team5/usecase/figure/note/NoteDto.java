package ntut.csie.team5.usecase.figure.note;

import ntut.csie.team5.usecase.figure.FigureDto;

import java.awt.*;

public class NoteDto extends FigureDto {

    private int leftTopPositionX;
    private int leftTopPositionY;
    private int rightBottomPositionX;
    private int rightBottomPositionY;
    private String color;
    private String figureType;

    public int getLeftTopPositionX() {
        return leftTopPositionX;
    }

    public int getLeftTopPositionY() {
        return leftTopPositionY;
    }

    public int getRightBottomPositionX() {
        return rightBottomPositionX;
    }

    public int getRightBottomPositionY() {
        return rightBottomPositionY;
    }

    public void setLeftTopPosition(Point leftTopPosition) {
        this.leftTopPositionX = leftTopPosition.x;
        this.leftTopPositionY = leftTopPosition.y;
    }

    public void setRightBottomPosition(Point rightBottomPosition) {
        this.rightBottomPositionX = rightBottomPosition.x;
        this.rightBottomPositionY = rightBottomPosition.y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }
}
