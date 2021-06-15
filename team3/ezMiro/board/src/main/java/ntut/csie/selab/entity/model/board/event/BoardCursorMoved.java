package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEvent;

import java.util.Date;
import java.util.Set;

public class BoardCursorMoved extends DomainEvent {
    private Cursor cursor;

    public BoardCursorMoved(Date date, Cursor cursor) {
        super(date);
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
