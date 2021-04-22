package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteDescriptionChanged extends DomainEvent {
    private final String noteId;
    private final String originalDescription;
    private final String newDescription;
    private final String boardId;

    public NoteDescriptionChanged(String noteId, String originalDescription, String newDescription, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.originalDescription = originalDescription;
        this.newDescription = newDescription;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public String originalDescription() {
        return originalDescription;
    }

    public String newDescription() {
        return newDescription;
    }

    public String boardId() {
        return boardId;
    }
}
