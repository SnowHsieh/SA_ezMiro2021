package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteDisplayOrderChanged extends DomainEvent {
    private final String noteId;
    private final int displayOrder;
    private final String boardId;

    public NoteDisplayOrderChanged(String noteId, int displayOrder, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.displayOrder = displayOrder;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public int displayOrder() {
        return displayOrder;
    }

    public String boardId() {
        return boardId;
    }
}