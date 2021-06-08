package ntut.csie.islab.miro.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class ConnectableFigureAttachedByLineDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final UUID srcConnectableFigureId;
    private final UUID destConnectableFigureId;

    public ConnectableFigureAttachedByLineDomainEvent(UUID boardId, UUID figureId, UUID srcConnectableFigureId, UUID destConnectableFigureId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.srcConnectableFigureId = srcConnectableFigureId;
        this.destConnectableFigureId = destConnectableFigureId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public UUID getSrcConnectableFigureId() {
        return srcConnectableFigureId;
    }

    public UUID getDestConnectableFigureId() {
        return destConnectableFigureId;
    }
}
