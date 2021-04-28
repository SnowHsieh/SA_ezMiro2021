package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.board.event.BoardCreated;

public class Board extends AggregateRoot<String> {
    private String teamId;
    private String boardName;
    public Board(String teamId, String boardId, String boardName) {
        super(boardId);
        this.teamId = teamId;
        this.boardName = boardName;

        addDomainEvent(new BoardCreated(teamId, boardId, boardName));
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
