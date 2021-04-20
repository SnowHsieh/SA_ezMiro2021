package ntut.csie.sslab.kanban.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;

public class Board extends AggregateRoot<String> {
    String boardId;
    String boardName;

    public Board(String boardId, String boardName) {
        super(boardId);
        this.boardId = boardId;
        this.boardName = boardName;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
