package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.team5.entity.model.figure.FigureType;

import java.awt.*;
import java.util.UUID;

public class NoteBuilder {

    private String noteId;
    private String boardId;
    private Point leftTopPosition;
    private Point rightBottomPosition;
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

    public NoteBuilder rightBottomPosition(Point rightBottomPosition) {
        this.rightBottomPosition = rightBottomPosition;
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
        Note note = new Note(noteId, boardId, leftTopPosition, rightBottomPosition, color, figureType);
        return note;
    }
}
