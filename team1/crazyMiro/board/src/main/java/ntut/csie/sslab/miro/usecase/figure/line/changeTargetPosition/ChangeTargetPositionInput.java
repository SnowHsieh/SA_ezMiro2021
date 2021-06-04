package ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public interface ChangeTargetPositionInput extends Input {
    String getFigureId();

    void setFigureId(String stickerId);

    Coordinate getTargetPosition();

    void setTargetPosition(Coordinate position);

}
