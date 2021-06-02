package ntut.csie.sslab.miro.usecase.line.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;

public interface MoveLineInput extends Input {
    String getLineId();

    void setLineId(String lineId);

    Coordinate getDelta();

    void setDelta(Coordinate delta);
}