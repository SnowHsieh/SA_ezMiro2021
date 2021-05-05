package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteDescriptionChanged extends DomainEvent {
    private final String noteId;
    private final String description;
    private final String boardId;

    public NoteDescriptionChanged(String noteId, String description, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.description = description;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public String description() {
        return description;
    }

    public String boardId() {
        return boardId;
    }
}