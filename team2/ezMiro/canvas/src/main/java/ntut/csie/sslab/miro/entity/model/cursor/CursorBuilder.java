package ntut.csie.sslab.miro.entity.model.cursor;

import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import java.util.UUID;

public class CursorBuilder {
    private String cursorId;
    private String boardId;
    private String userId;
    private Coordinate coordinate;

    public static CursorBuilder newInstance() {
        return new CursorBuilder();
    }

    public CursorBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public CursorBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public CursorBuilder coordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return this;
    }

    public Cursor build() {
        cursorId = UUID.randomUUID().toString();
        Cursor cursor = new Cursor(cursorId, boardId, userId, coordinate);
        return cursor;
    }
}