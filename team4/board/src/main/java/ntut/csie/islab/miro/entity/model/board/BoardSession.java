package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class BoardSession extends ValueObject {

    private UUID boardId;
    private UUID userId;
    private BoardSessionId boardSessionId;

    public BoardSession(UUID boardId, UUID userId, BoardSessionId boardSessionId) {
        this.boardId = boardId;
        this.userId = userId;
        this.boardSessionId = boardSessionId;
    }

    public UUID boardId() {
        return boardId;
    }

    public UUID userId() {
        return userId;
    }

    public BoardSessionId boardSessionId() {
        return boardSessionId;
    }
}
