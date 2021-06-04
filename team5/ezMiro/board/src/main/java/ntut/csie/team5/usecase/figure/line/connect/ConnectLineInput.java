package ntut.csie.team5.usecase.figure.line.connect;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ConnectLineInput extends Input {

    String getLineId();

    void setLineId(String lineId);

    String getEndpointId();

    void setEndpointId(String endpointId);

    String getFigureId();

    void setFigureId(String figureId);
}
