package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NoteDeleted extends DomainEvent {
    private final String boardId;

    public NoteDeleted(String boardId) {
        super(DateProvider.now());
        this.boardId = boardId;
    }

    public String boardId() {
        return boardId;
    }
}