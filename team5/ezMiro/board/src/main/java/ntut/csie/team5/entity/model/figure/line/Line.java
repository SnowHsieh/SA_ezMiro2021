package ntut.csie.team5.entity.model.figure.line;

import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.FigureType;

public class Line extends Figure {

    private Endpoint endpointA;
    private Endpoint endpointB;

    public Line(String figureId, String boardId, Endpoint endpointA, Endpoint endpointB, FigureType figureType) {
        super(figureId, boardId, figureType);
        this.endpointA = endpointA;
        this.endpointB = endpointB;
    }

    public Endpoint getEndpointA() {
        return endpointA;
    }

    public void setEndpointA(Endpoint endpointA) {
        this.endpointA = endpointA;
    }

    public Endpoint getEndpointB() {
        return endpointB;
    }

    public void setEndpointB(Endpoint endpointB) {
        this.endpointB = endpointB;
    }
}
