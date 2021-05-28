package ntut.csie.team5.entity.model.figure.note;

import ntut.csie.team5.entity.model.figure.FigureType;

import java.util.UUID;

public class NoteBuilder {

    private String noteId;
    private String boardId;
//    private Point leftTopPosition;
    private int leftTopPositionX;
    private int leftTopPositionY;
    private int height;
    private int width;
    private String color;
    private FigureType figureType;

    public static NoteBuilder newInstance() {
        return new NoteBuilder();
    }

    public NoteBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public NoteBuilder leftTopPositionX(int leftTopPositionX) {
        this.leftTopPositionX = leftTopPositionX;
        return this;
    }

    public NoteBuilder leftTopPositionY(int leftTopPositionY) {
        this.leftTopPositionY = leftTopPositionY;
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

    public NoteBuilder color(String color) {
        this.color = color;
        return this;
    }

    public NoteBuilder figureType(FigureType figureType) {
        this.figureType = figureType;
        return this;
    }

    public Note build() {
        noteId = UUID.randomUUID().toString();
        Note note = new Note(noteId, boardId, leftTopPositionX, leftTopPositionY, height, width, color, figureType);
        return note;
    }
}
