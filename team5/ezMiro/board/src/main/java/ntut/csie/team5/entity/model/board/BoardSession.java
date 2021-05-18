package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class BoardSession extends ValueObject {

    private final String boardId;
    private final String userId;
    private final BoardSessionId boardSessionId;

    public BoardSession(String boardId, String userId, BoardSessionId boardSessionId) {
        this.boardId = boardId;
        this.userId = userId;
        this.boardSessionId = boardSessionId;
    }

    public String boardId() {
        return boardId;
    }

    public String userId() {
        return userId;
    }

    public BoardSessionId boardSessionId() {
        return boardSessionId;
    }
}
