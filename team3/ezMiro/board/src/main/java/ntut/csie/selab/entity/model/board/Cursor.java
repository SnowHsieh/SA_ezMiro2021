package ntut.csie.selab.entity.model.board;

import java.awt.*;

public class Cursor {
    private String userId;
    private Point point;

    public Cursor(String userId, Point point) {
        this.userId = userId;
        this.point = point;
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
