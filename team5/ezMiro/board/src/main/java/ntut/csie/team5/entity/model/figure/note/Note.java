package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.sslab.ddd.model.Entity;
import ntut.csie.team5.entity.model.figure.ConnectableFigure;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.FigureType;

import java.awt.*;

public class Note extends ConnectableFigure {

    private Color color;

    public Note(String noteId, String boardId, Point leftTopPosition, int height, int width, Color color, FigureType figureType) {
        super(noteId, boardId, leftTopPosition, height, width, figureType);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
