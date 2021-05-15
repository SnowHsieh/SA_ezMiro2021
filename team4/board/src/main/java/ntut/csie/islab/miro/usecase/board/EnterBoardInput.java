package ntut.csie.islab.miro.usecase.board;

import java.util.UUID;

public class EnterBoardInput {
    private UUID boardId;
    private UUID userId;

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
}
