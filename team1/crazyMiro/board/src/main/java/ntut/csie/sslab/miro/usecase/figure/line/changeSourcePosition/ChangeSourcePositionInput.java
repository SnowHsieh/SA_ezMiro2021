package ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public interface ChangeSourcePositionInput extends Input {
    String getFigureId();

    void setFigureId(String figureId);

    Coordinate getSourcePosition();

    void setSourcePosition(Coordinate position);
}
