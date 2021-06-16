package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class BoardSession extends ValueObject {

    private UUID boardId;
    private UUID userId;
    private BoardSessionId boardSessionId;
    private Position cursorPosition;

    public BoardSession(UUID boardId, UUID userId, BoardSessionId boardSessionId) {
        this.boardId = boardId;
        this.userId = userId;
        this.boardSessionId = boardSessionId;
        this.cursorPosition = new Position(0.0, 0.0);
    }

    public BoardSession(UUID boardId, UUID userId, BoardSessionId boardSessionId, Position cursorPosition) {
        this.boardId = boardId;
        this.userId = userId;
        this.boardSessionId = boardSessionId;
        this.cursorPosition = cursorPosition;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getUserId() {
        return userId;
    }

    public BoardSessionId getBoardSessionId() {
        return boardSessionId;
    }

    public Position getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(Position cursorPosition) {
        this.cursorPosition = cursorPosition;
    }
}
