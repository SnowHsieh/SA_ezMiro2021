package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedFigure extends ValueObject {

    private String figureId;
    private String boardId;
    private int zOrder;

    public CommittedFigure(String figureId, String boardId, int zOrder) {
        this.figureId = figureId;
        this.boardId = boardId;
        this.zOrder = zOrder;
    }

    public String figureId() {
        return figureId;
    }

    public String boardId() {
        return boardId;
    }

    public int zOrder() {
        return zOrder;
    }

}
