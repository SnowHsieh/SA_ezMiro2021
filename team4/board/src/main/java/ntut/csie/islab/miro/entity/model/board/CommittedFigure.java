package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

import java.util.UUID;

public class CommittedFigure extends ValueObject {
    private UUID boardId;
    private UUID figureId;
    private FigureTypeEnum figureType;

    public CommittedFigure(UUID boardId, UUID figureId, FigureTypeEnum figureType) {
        this.boardId = boardId;
        this.figureId = figureId;
        this.figureType = figureType;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public FigureTypeEnum getFigureType() {
        return figureType;
    }

}
