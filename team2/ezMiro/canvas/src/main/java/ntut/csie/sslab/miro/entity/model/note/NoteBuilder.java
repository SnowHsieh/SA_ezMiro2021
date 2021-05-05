package ntut.csie.sslab.miro.entity.model.note;

import java.util.UUID;

public class NoteBuilder {
    private String boardId;
    private String noteId;
    private String description;
    private String color;
    private Coordinate coordinate;
    private double width;
    private double height;
    private int displayOrder;

    public static NoteBuilder newInstance() { return new NoteBuilder(); }

    public NoteBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public NoteBuilder noteId(String noteId) {
        this.noteId = noteId;
        return this;
    }

    public NoteBuilder description(String description) {
        this.description = description;
        return this;
    }

    public NoteBuilder color(String color) {
        this.color = color;
        return this;
    }

    public NoteBuilder coordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public NoteBuilder width(double width) {
        this.width = width;
        return this;
    }

    public NoteBuilder height(double height) {
        this.height = height;
        return this;
    }

    public NoteBuilder displayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public Note build() {
        noteId = UUID.randomUUID().toString();
        Note note = new Note(boardId, noteId, description, color, coordinate, width, height, displayOrder);
        return note;
    }
}
