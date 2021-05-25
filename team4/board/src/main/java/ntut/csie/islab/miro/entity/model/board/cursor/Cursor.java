package ntut.csie.islab.miro.entity.model.board.cursor;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class Cursor extends ValueObject {
    private UUID userId;
    private UUID boardId;
    private Position position;

    public Cursor(UUID userId, UUID boardId) {
        this.userId = userId;
        this.boardId = boardId;
        this.position = new Position(0.0,0.0);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}


