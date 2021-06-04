package ntut.csie.team5.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

public class LineConnected extends DomainEvent {

    private final String lineId;
    private final String endpointId;
    private final String connectedFigureId;
    private final String boardId;
    private final FigureType figureType;

    public LineConnected(String lineId, String endpointId, String connectedFigureId, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.lineId = lineId;
        this.endpointId = endpointId;
        this.connectedFigureId = connectedFigureId;
        this.boardId = boardId;
        this.figureType = figureType;
    }

    public String lineId() {
        return lineId;
    }

    public String endpointId() {
        return endpointId;
    }

    public String connectedFigureId() {
        return connectedFigureId;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}
