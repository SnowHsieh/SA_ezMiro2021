package ntut.csie.team5.usecase.figure.note;

import ntut.csie.team5.usecase.figure.FigureDto;

import java.awt.*;

public class NoteDto extends FigureDto {

    private int x;
    private int y;
    private String color;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(Point position) {
        this.x = position.x;
        this.y = position.y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

}
