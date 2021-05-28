package ntut.csie.selab.entity.model.board;

import ntut.csie.selab.model.ValueObject;

import java.awt.*;

public class Cursor extends ValueObject {
    private String boardId;
    private String userId;
    private Point point;

    public Cursor(String boardId, String userId, Point point) {
        this.boardId = boardId;
        this.userId = userId;
        this.point = point;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getUserId() {
        return userId;
    }

    public Point getPoint() {
        return point;
    }
}
