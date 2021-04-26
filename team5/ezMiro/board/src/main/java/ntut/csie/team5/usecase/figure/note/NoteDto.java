package ntut.csie.team5.usecase.figure.note;

import ntut.csie.team5.usecase.figure.FigureDto;

public class NoteDto extends FigureDto {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
