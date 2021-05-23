package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.Entity;

public class BoardSession extends Entity {
    private String userId;
    private String boardId;
    private String boardSessionId;

    public BoardSession(String userId, String boardId, String boardSessionId) {
        super(boardSessionId);
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
