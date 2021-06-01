package ntut.csie.team5.usecase.figure.line.draw;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.line.Endpoint;

public interface DrawLineInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    Endpoint getEndpointA();

    void setEndpointA(Endpoint endpoint);

    Endpoint getEndpointB();

    void setEndpointB(Endpoint endpoint);

    FigureType getFigureType();

    void setFigureType(FigureType line);
}
