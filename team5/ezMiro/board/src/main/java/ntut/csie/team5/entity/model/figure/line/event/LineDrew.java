package ntut.csie.team5.entity.model.figure.line.event;

import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.event.FigureCreated;
import ntut.csie.team5.entity.model.figure.line.Endpoint;

public class LineDrew extends FigureCreated {

    private final Endpoint endpointA;
    private final Endpoint endpointB;

    public LineDrew(String figureId, String boardId, Endpoint endpointA, Endpoint endpointB, FigureType figureType) {
        super(figureId, boardId, figureType);
        this.endpointA = endpointA;
        this.endpointB = endpointB;
    }

    public Endpoint endpointA() {
        return endpointA;
    }

    public Endpoint endpointB() {
        return endpointB;
    }
}
