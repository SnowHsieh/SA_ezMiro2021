package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedFigure extends ValueObject {

    private String figureId;
    private String boardId;
    private int order;

    public CommittedFigure(String figureId, String boardId, int order) {
        this.figureId = figureId;
        this.boardId = boardId;
        this.order = order;
    }

    public String getFigureId() {
        return figureId;
    }

    public void setFigureId(String figureId) {
        this.figureId = figureId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
