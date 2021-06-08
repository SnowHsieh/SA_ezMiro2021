package ntut.csie.islab.miro.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class ConnectableFigureUnattachedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final UUID connectableFigureIdToBeUnattached;
    private final String attachEndPointKind;

    public ConnectableFigureUnattachedDomainEvent(UUID boardId, UUID figureId, UUID connectableFigureIdToBeUnattached, String attachEndPointKind) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.connectableFigureIdToBeUnattached = connectableFigureIdToBeUnattached;
        this.attachEndPointKind = attachEndPointKind;
    }
    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public UUID getConnectableFigureIdToBeUnattached() {
        return connectableFigureIdToBeUnattached;
    }

    public String getAttachEndPointKind() {
        return attachEndPointKind;
    }
}
