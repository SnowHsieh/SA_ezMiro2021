package ntut.csie.selab.entity.model.widget;

import java.awt.*;

public class Coordinate{

    Point topLeft;
    Point bottomRight;

    public Coordinate(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        topLeft = new Point(topLeftX, topLeftY);
        bottomRight = new Point(bottomRightX, bottomRightY);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }
}
