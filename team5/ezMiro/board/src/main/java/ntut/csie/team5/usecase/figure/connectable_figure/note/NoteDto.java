package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.team5.usecase.figure.FigureDto;

public class NoteDto extends FigureDto {

    private int leftTopPositionX;
    private int leftTopPositionY;
    private int height;
    private int width;
    private String color;
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

    public void setLeftTopPositionX(int leftTopPositionX) {
        this.leftTopPositionX = leftTopPositionX;
    }

    public void setLeftTopPositionY(int leftTopPositionY) {
        this.leftTopPositionY = leftTopPositionY;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
