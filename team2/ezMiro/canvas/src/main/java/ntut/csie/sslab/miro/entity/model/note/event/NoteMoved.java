package ntut.csie.sslab.miro.entity.model.note.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;

public class NoteMoved extends DomainEvent {
    private final String noteId;
    private final Coordinate coordinate;
    private final String boardId;

    public NoteMoved(String noteId, Coordinate coordinate, String boardId) {
        super(DateProvider.now());
        this.noteId = noteId;
        this.coordinate = coordinate;
        this.boardId = boardId;
    }

    public String noteId() {
        return noteId;
    }

    public Coordinate coordinate() {
        return coordinate;
    }

    public String boardId() {
        return boardId;
    }
}