package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;

public class NoteCreated extends DomainEvent {

    private final String boardId;
    private final String noteId;
    private final String description;
    private final String color;
    private final Coordinate coordinate;
    private final double width;
    private final double height;

    public NoteCreated(String boardId, String noteId, String description, String color, Coordinate coordinate, double width, double height) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.noteId = noteId;
        this.description = description;
        this.color = color;
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;
    }

    public String boardId() { return boardId; }

    public String noteId() { return noteId; }

    public String description() { return description; }

    public String color() { return color; }

    public Coordinate coordinate() { return coordinate; }

    public double width() { return width; }

    public double height() { return height; }
}
