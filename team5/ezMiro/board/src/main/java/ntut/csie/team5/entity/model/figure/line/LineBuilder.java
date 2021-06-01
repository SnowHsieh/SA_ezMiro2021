package ntut.csie.team5.entity.model.figure.line;

import ntut.csie.team5.entity.model.figure.FigureType;

import java.util.UUID;

public class LineBuilder {

    private String lineId;
    private String boardId;
    private Endpoint endpointA;
    private Endpoint endpointB;
    private FigureType figureType;

    public static LineBuilder newInstance() {
        return new LineBuilder();
    }

    public LineBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public LineBuilder endpointA(Endpoint endpointA) {
        this.endpointA = endpointA;
        return this;
    }

    public LineBuilder endpointB(Endpoint endpointB) {
        this.endpointB = endpointB;
        return this;
    }

    public LineBuilder figureType(FigureType figureType) {
        this.figureType = figureType;
        return this;
    }

    public Line build() {
        lineId = UUID.randomUUID().toString();
        Line line = new Line(lineId, boardId, endpointA, endpointB, figureType);
        return line;
    }
}
