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
    private final UUID srcTextFigureId;
    private final UUID destTextFigureId;

    public TextfigureAttachedByLineDomainEvent(UUID boardId, UUID figureId, UUID srcTextFigureId, UUID destTextFigureId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.srcTextFigureId = srcTextFigureId;
        this.destTextFigureId = destTextFigureId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public UUID getSrcTextFigureId() {
        return srcTextFigureId;
    }

    public UUID getDestTextFigureId() {
        return destTextFigureId;
    }
}
