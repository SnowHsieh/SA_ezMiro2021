package ntut.csie.team5.usecase.figure.line.connect;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ConnectLineInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);

    String getEndpointId();

    void setEndpointId(String endpointId);

    String getConnectedFigureId();

    void setConnectedFigureId(String connectedFigureId);
}
