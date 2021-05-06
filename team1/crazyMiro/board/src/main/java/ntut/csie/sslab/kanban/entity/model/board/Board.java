package ntut.csie.sslab.kanban.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardCreated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board extends AggregateRoot<String> {
    String boardId;
    String boardName;
    List<CommittedFigure> figures;

    public Board(String boardId, String boardName) {
        super(boardId);
        this.boardId = boardId;
        this.boardName = boardName;
        this.figures = new ArrayList<>();

        addDomainEvent(new BoardCreated(boardId));
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

    public List<CommittedFigure> getFigures() {
        return figures;
    }
}
