package ntut.csie.sslab.kanban.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;

public class FigureCommitted extends DomainEvent {
    private String boardId;
    private String figureId;

    public FigureCommitted( String boardId, String figureId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getFigureId() {
        return figureId;
    }
}
