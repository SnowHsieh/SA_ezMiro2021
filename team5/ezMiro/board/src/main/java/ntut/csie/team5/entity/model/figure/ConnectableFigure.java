package ntut.csie.team5.entity.model.figure;

import java.awt.*;

public abstract class ConnectableFigure extends Figure {

    private Point position;

    public ConnectableFigure(String figureId, String boardId, Point position, FigureType figureType) {
        super(figureId, boardId, figureType);
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
