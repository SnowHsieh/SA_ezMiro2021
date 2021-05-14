package ntut.csie.sslab.kanban.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class BoardSession extends ValueObject {
    private final String userId;
    private final String boardId;
    private final String boardSessionId;

    public BoardSession(String userId, String boardId, String boardSessionId) {
        this.userId = userId;
        this.boardId = boardId;
        this.boardSessionId = boardSessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBoardSessionId() {
        return boardSessionId;
    }
}
