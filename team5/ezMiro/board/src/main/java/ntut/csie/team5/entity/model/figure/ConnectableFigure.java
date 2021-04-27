package ntut.csie.team5.entity.model.figure;

import java.awt.*;

public abstract class ConnectableFigure extends Figure {

    private Point leftTopPosition;
    private Point rightBottomPosition;

    public ConnectableFigure(String figureId, String boardId, Point leftTopPosition, Point rightBottomPosition, FigureType figureType) {
        super(figureId, boardId, figureType);
        this.leftTopPosition = leftTopPosition;
        this.rightBottomPosition = rightBottomPosition;
    }

    public Point getLeftTopPosition() {
        return leftTopPosition;
    }

    public void setLeftTopPosition(Point leftTopPosition) {
        this.leftTopPosition = leftTopPosition;
    }

    public Point getRightBottomPosition() {
        return rightBottomPosition;
    }

    public void setRightBottomPosition(Point rightBottomPosition) {
        this.rightBottomPosition = rightBottomPosition;
    }
}
