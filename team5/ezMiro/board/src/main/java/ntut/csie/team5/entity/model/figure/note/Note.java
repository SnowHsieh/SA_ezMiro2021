package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.sslab.ddd.model.Entity;
import ntut.csie.team5.entity.model.figure.ConnectableFigure;

import java.awt.*;

public class Note extends ConnectableFigure {

    private Color color;

    public Note(String noteId, String boardId, Point position, Color color) {
        super(noteId, boardId, position);
        this.color = color;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
