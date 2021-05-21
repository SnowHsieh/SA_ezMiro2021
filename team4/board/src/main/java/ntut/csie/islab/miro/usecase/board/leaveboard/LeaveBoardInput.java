package ntut.csie.islab.miro.usecase.board.leaveboard;

import java.util.UUID;

public class LeaveBoardInput {
    private UUID boardId;
    private UUID userId;
    private String boardSessionId;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getBoardSessionId() {
        return boardSessionId;
    }

    public void setBoardSessionId(String boardSessionId) {
        this.boardSessionId = boardSessionId;
    }
}
