package ntut.csie.team5.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.team5.entity.model.board.ZOrderType;

public class FigureZOrderChanged extends DomainEvent {

    private String figureId;
    private String boardId;
    private ZOrderType zOrderType;

    public FigureZOrderChanged(String figureId, String boardId, ZOrderType zOrderType) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
        this.zOrderType = zOrderType;
    }

    public String figureId() {
        return figureId;
    }

    public String boardId() {
        return boardId;
    }

    public ZOrderType zOrderType() {
        return zOrderType;
    }
}
