package ntut.csie.team5.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.figure.FigureType;

import java.util.Date;

public class FigureCreated extends DomainEvent {

    private final String figureId;
    private final String boardId;
    private final FigureType figureType;

    public FigureCreated(String figureId, String boardId, FigureType figureType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
        this.figureType = figureType;
    }

    public String figureId() {
        return figureId;
    }

    public String boardId() {
        return boardId;
    }

    public FigureType figureType() {
        return figureType;
    }
}
