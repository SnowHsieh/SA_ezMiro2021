package ntut.csie.sslab.miro.usecase.line.move;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;

public interface MoveLinePointInput extends Input {
    String getLineId();

    void setLineId(String lineId);

    Coordinate getPointDelta();

    void setPointDelta(Coordinate delta);

    LinePoint getLinePoint();

    void setLinePoint(LinePoint linePoint);
}