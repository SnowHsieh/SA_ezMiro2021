package ntut.csie.sslab.miro.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class CursorCreated extends DomainEvent {
    private final String boardId;
    private final String cursorId;
    private final String userId;

    public CursorCreated(String userId, String boardId, String cursorId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.cursorId = cursorId;
        this.userId = userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getCursorId() {
        return cursorId;
    }

    public String getUserId() {
        return userId;
    }

}
