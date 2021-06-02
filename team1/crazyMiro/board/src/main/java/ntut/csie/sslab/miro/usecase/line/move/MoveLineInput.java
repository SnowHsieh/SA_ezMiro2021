package ntut.csie.sslab.miro.usecase.line.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public interface MoveLineInput extends Input {

    void setLineId(String lineId);

    String getLineId();

    void setSourcePosition(Coordinate sourcePosition);

    Coordinate getSourcePosition();

    void setTargetPosition(Coordinate targetPosition);

    Coordinate getTargetPosition();
}
