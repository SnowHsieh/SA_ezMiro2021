package ntut.csie.sslab.kanban.entity.model.board2;

import ntut.csie.sslab.ddd.model.Entity;


public class BoardSession extends Entity<String> {
    private String userId;
    private String boardId;

    public BoardSession(String boardSessionId, String userId, String boardId) {
        super(boardSessionId);
        this.userId = userId;
        this.boardId = boardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardSessionId() { return getId(); }
}
