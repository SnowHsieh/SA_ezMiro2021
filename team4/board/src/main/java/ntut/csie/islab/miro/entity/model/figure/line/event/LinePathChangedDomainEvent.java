package ntut.csie.islab.miro.entity.model.figure.line.event;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.List;
import java.util.UUID;

public class LinePathChangedDomainEvent extends DomainEvent {
    private UUID boardId;
    private UUID figureId;
    private List<Position> originalPositionList;
    private List<Position> newPositionList;

    public LinePathChangedDomainEvent(UUID boardId, UUID figureId, List<Position> originalPositionList, List<Position> positionList) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.originalPositionList = originalPositionList;
        this.newPositionList = positionList;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public List<Position> getOriginalPositionList() {
        return originalPositionList;
    }

    public List<Position> getNewPositionList() {
        return newPositionList;
    }
}
