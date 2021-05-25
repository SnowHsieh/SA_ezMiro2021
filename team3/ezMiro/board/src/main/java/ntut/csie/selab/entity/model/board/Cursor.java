package ntut.csie.selab.entity.model.board;

import java.awt.*;

// TODO: 要改成value object，移除setter
public class Cursor {
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

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) { this.point = point; }
}
