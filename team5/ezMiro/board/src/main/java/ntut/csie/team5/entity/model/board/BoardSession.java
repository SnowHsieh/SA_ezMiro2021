package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class BoardSession extends ValueObject {

    private final String boardId;
    private final String boardSessionId;
    private final String userId;

    public BoardSession(String boardId, String boardSessionId, String userId) {
        this.boardId = boardId;
        this.boardSessionId = boardSessionId;
        this.userId = userId;
    }

    public String boardId() {
        return boardId;
    }

    public String boardSessionId() {
        return boardSessionId;
    }

    public String userId() {
        return userId;
    }
}
