package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteColorChanged extends DomainEvent {
    private final String noteId;
    private final String color;
    private final String boardId;

    public NoteColorChanged(String noteId, String color, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.color = color;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public String color() {
        return color;
    }

    public String boardId() {
        return boardId;
    }
}