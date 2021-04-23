package ntut.csie.team5.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class FigureCommitted extends DomainEvent {

    private String figureId;
    private String boardId;

    public FigureCommitted(String figureId, String boardId) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
    }

    public String figureId() {
        return figureId;
    }

    public String boardId() {
        return boardId;
    }

}
