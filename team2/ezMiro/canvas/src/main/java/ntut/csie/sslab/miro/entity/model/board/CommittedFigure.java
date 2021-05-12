package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedFigure extends ValueObject {
    private String boardId;
    private String figureId;
    private int zOrder;

    public CommittedFigure(String boardId, String figureId, int zOrder) {
        this.boardId = boardId;
        this.figureId = figureId;
        this.zOrder = zOrder;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getFigureId() {
        return figureId;
    }

    public int getZOrder() {
        return zOrder;
    }
}