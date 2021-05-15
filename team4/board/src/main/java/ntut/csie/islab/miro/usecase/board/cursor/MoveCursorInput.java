package ntut.csie.islab.miro.usecase.board.cursor;

import ntut.csie.islab.miro.entity.model.Position;

import java.util.UUID;

public class MoveCursorInput {
    private UUID userId;
    private UUID boardId;
    private Position position;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }
}
