package ntut.csie.team5.usecase.figure.line.move_endpoint;

import ntut.csie.sslab.ddd.usecase.Input;

public interface MoveLineEndpointInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);

    String getEndpointId();

    void setEndpointId(String endpointId);

    int getPositionX();

    void setPositionX(int positionX);

    int getPositionY();

    void setPositionY(int positionY);
}
