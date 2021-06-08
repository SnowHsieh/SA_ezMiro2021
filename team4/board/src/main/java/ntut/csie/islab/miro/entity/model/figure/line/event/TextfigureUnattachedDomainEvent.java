package ntut.csie.islab.miro.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class TextfigureUnattachedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final UUID textFigureIdToBeUnattached;
    private final String attachEndPointKind;

    public TextfigureUnattachedDomainEvent(UUID boardId, UUID figureId, UUID textFigureIdToBeUnattached, String attachEndPointKind) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.textFigureIdToBeUnattached = textFigureIdToBeUnattached;
        this.attachEndPointKind = attachEndPointKind;
    }
    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public UUID getTextFigureIdToBeUnattached() {
        return textFigureIdToBeUnattached;
    }

    public String getAttachEndPointKind() {
        return attachEndPointKind;
    }
}
