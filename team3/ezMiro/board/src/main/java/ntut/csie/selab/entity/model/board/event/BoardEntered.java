package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEvent;

import java.util.Date;
import java.util.Set;

public class BoardEntered extends DomainEvent {
    public String boardId;
    public Set<Cursor> cursors;

    public BoardEntered(Date occurredOn, String boardId, Set<Cursor> cursors) {
        super(occurredOn);

        this.boardId = boardId;
        this.cursors = cursors;
    }

    public String getBoardId() {
        return boardId;
    }

    public Set<Cursor> getCursors() {
        return cursors;
    }
}
