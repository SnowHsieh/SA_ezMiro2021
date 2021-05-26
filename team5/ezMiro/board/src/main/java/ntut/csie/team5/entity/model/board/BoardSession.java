package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class BoardSession extends ValueObject {

    private final String boardSessionId;
    private final String boardId;
    private final String userId;

    public BoardSession(String boardSessionId, String boardId, String userId) {
        this.boardSessionId = boardSessionId;
        this.boardId = boardId;
        this.userId = userId;
    }

    public String boardId() {
        return boardId;
    }

    public String userId() {
        return userId;
    }

    public String boardSessionId() {
        return boardSessionId;
    }
}
