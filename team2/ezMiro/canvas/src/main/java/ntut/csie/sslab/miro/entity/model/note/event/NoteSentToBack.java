package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteSentToBack extends DomainEvent {
    private final String noteId;
    private final String boardId;

    public NoteSentToBack(String noteId, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public String boardId() {
        return boardId;
    }
}