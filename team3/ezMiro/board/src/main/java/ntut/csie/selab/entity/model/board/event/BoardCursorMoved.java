package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEvent;

import java.util.Date;
import java.util.Set;

public class BoardCursorMoved extends DomainEvent {
    private String boardId;
    private Set<Cursor> cursors;

    public BoardCursorMoved(Date date, String boardId, Set<Cursor> cursors) {
        super(date);

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
