package ntut.csie.team5.usecase.figure.line.move;

import ntut.csie.sslab.ddd.usecase.Input;

public interface MoveLineInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);

    int getOffsetX();

    void setOffsetX(int offsetX);

    int getOffsetY();

    void setOffsetY(int offsetY);
}
