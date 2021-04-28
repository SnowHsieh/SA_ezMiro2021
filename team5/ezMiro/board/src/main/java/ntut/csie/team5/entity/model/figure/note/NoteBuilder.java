package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.team5.entity.model.figure.FigureType;

import java.awt.*;
import java.util.UUID;

public class NoteBuilder {

    private String noteId;
    private String boardId;
    private Point leftTopPosition;
    private int height;
    private int width;
    private Color color;
    private FigureType figureType;

    public static NoteBuilder newInstance() {
        return new NoteBuilder();
    }

    public NoteBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public NoteBuilder leftTopPosition(Point leftTopPosition) {
        this.leftTopPosition = leftTopPosition;
        return this;
    }

    public NoteBuilder height(int height) {
        this.height = height;
        return this;
    }

    public NoteBuilder width(int width) {
        this.width = width;
        return this;
    }

    public NoteBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public NoteBuilder figureType(FigureType figureType) {
        this.figureType = figureType;
        return this;
    }

    public Note build() {
        noteId = UUID.randomUUID().toString();
        Note note = new Note(noteId, boardId, leftTopPosition, height, width, color, figureType);
        return note;
    }
}
