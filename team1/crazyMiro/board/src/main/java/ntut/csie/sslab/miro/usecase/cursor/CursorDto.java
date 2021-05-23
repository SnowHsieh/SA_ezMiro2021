package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.miro.entity.model.Coordinate;

public class CursorDto {
    private final String boardId;
    private final String userId;
    private final String cursorId;
    private final Coordinate position;

    public CursorDto(String boardId, String userId, String cursorId, Coordinate position) {
        this.boardId = boardId;
        this.userId = userId;
        this.cursorId = cursorId;
        this.position = position;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCursorId() {
        return cursorId;
    }

    public Coordinate getPosition() {
        return position;
    }
}
