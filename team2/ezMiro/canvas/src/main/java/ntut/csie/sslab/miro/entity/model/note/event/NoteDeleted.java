package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteDeleted extends DomainEvent {
    private final String boardId;
    private final String noteId;

    public NoteDeleted(String boardId,String noteId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.noteId = noteId;
    }

    public String boardId() {
        return boardId;
    }

    public String noteId() {
        return noteId;
    }
}