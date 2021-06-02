package ntut.csie.sslab.miro.usecase.line.connecttofigure;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;

public interface ConnectLineToFigureInput extends Input {
    String getLineId();

    void setLineId(String lineId);

    String getFigureId();

    void setFigureId(String figureId);

    LinePoint getLinePoint();

    void setLinePoint(LinePoint linePoint);

    Coordinate getOffset();

    void setOffset(Coordinate offset);
}