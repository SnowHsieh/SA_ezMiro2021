package ntut.csie.islab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FigureChangedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final List<UUID> figureOrderList;

    public FigureChangedDomainEvent(UUID boardId, List<UUID> figureOrderList) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureOrderList = figureOrderList;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public List<UUID> getFigureOrderList() {
        return figureOrderList;
    }
}
