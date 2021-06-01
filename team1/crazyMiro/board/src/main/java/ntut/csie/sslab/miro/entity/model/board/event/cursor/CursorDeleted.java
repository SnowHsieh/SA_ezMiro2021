package ntut.csie.sslab.miro.entity.model.board.event.cursor;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;


public class CursorDeleted extends DomainEvent {
    private final String boardId;
    private final String userId;

    public CursorDeleted(String boardId, String userId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.userId = userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getUserId() {
        return userId;
    }
}
