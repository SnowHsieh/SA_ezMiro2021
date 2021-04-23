package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedFigure extends ValueObject {

    private String figureId;
    private String boardId;

    public CommittedFigure(String figureId, String boardId) {
        this.figureId = figureId;
        this.boardId = boardId;
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
}
