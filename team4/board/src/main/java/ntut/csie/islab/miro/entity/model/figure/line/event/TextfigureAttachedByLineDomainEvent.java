package ntut.csie.islab.miro.entity.model.figure.line.event;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TextfigureAttachedByLineDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final List<UUID> attachedTextFigureIdList;

    public TextfigureAttachedByLineDomainEvent(UUID boardId, UUID figureId, List<UUID> attachedTextFigureIdList) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.attachedTextFigureIdList = attachedTextFigureIdList;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public List<UUID> getAttachedTextFigureIdList() {
        return attachedTextFigureIdList;
    }
}
