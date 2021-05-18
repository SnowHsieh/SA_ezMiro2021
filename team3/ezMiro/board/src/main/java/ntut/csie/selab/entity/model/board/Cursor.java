package ntut.csie.selab.entity.model.board;

import java.awt.*;

public class Cursor {
    private String userId;
    private Point coordinate;

    public Cursor(String userId, Point coordinate) {
        this.userId = userId;
        this.coordinate = coordinate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Point getCoordinate() {
        return coordinate;
    }
}
