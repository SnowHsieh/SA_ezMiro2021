package ntut.csie.team5.entity.model.figure;

import ntut.csie.sslab.ddd.model.AggregateRoot;

public abstract class Figure extends AggregateRoot<String> {

    private String boardId;

    public Figure(String figureId, String boardId) {
        super(figureId);
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
