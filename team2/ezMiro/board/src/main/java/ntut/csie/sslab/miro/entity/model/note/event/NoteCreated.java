package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteCreated extends DomainEvent {

    private final String boardId;
    private final String noteId;
    private final String description;
    private final String color;

    public NoteCreated(String boardId, String noteId, String description, String color) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.noteId = noteId;
        this.description = description;
        this.color = color;
    }

    public String boardId() { return boardId; }

    public String noteId() { return noteId; }

    public String description() { return description; }

    public String color() { return color; }
}
