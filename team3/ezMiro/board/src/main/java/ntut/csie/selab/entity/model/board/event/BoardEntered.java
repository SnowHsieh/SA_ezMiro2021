package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEvent;

import java.util.Date;
import java.util.Set;

public class BoardEntered extends DomainEvent {
    private String boardId;
    private Cursor cursor;

    public BoardEntered(Date occurredOn, String boardId, Cursor cursor) {
        super(occurredOn);

        this.boardId = boardId;
        this.cursor = cursor;
    }

    public String getBoardId() {
        return boardId;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
