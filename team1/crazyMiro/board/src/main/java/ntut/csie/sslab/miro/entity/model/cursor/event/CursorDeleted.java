package ntut.csie.sslab.miro.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;


public class CursorDeleted extends DomainEvent {
    private final String boardId;
    private final String userId;
    private final String cursorId;

    public CursorDeleted(String boardId, String userId, String cursorId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
        this.cursorId = cursorId;
    }

    public String getCursorId() {
        return cursorId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getUserId() {
        return userId;
    }
}
