package ntut.csie.islab.miro.usecase.board.getallusercursors;

import java.util.UUID;

public class GetAllUserCursorsInput {
    private UUID boardId;
    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getBoardId() {
        return boardId;
    }
}
