package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class BoardSession extends ValueObject {

    private final String boardId;
    private final String userId;
    private final String boardSessionId;

    public BoardSession(String boardId, String userId) {
        this.boardId = boardId;
        this.userId = userId;
        this.boardSessionId = UUID.randomUUID().toString();
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
