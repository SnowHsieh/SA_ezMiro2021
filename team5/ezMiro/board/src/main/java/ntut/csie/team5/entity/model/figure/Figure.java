package ntut.csie.team5.entity.model.figure;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.team5.entity.model.figure.event.FigureCreated;

public abstract class Figure extends AggregateRoot<String> {

    private String boardId;
    private FigureType figureType;

    public Figure(String figureId, String boardId, FigureType figureType) {
        super(figureId);
        this.boardId = boardId;
        this.figureType = figureType;

        addDomainEvent(new FigureCreated(figureId, boardId, figureType));
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public FigureType getFigureType() {
        return figureType;
    }

    public void setFigureType(FigureType figureType) {
        this.figureType = figureType;
    }
}
