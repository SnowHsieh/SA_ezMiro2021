package ntut.csie.islab.miro.usecase.board;

import java.util.UUID;

public class GetBoardContentInput {
    private UUID boardId;
    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getBoardId() {
        return boardId;
    }
}
