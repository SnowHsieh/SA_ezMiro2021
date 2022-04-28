package ntut.csie.islab.miro.entity.model.figure.line.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class LineColorChangedDomainEvent extends DomainEvent {
    private final UUID boardId;
    private final UUID figureId;
    private final String originalColor;
    private final String newColor;

    public LineColorChangedDomainEvent(UUID boardId, UUID figureId, String originalColor, String newColor) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.originalColor = originalColor;
        this.newColor = newColor;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public String getOriginalColor() {
        return originalColor;
    }

    public String getNewColor() {
        return newColor;
    }
}
