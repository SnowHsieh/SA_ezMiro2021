package ntut.csie.sslab.kanban.entity.model.cursor.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class CusorCreated extends DomainEvent {
    private String boardId;
    private String cursorId;
    private String ip;
    private String sessionId;

    public CusorCreated(String boardId, String cursorId, String ip, String sessionId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.cursorId = cursorId;
        this.ip = ip;
        this.sessionId = sessionId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getCursorId() {
        return cursorId;
    }

    public String getIp() {
        return ip;
    }

    public String getSessionId() {
        return sessionId;
    }
}
