package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardCreated extends DomainEvent {
    private final String teamId;
    private final String boardId;
    private final String boardName;

    public BoardCreated(String teamId, String boardId, String boardName) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
        this.boardName = boardName;
    }

    public String teamId() {
        return teamId;
    }

    public String boardId() {
        return boardId;
    }

    public String boardName() {
        return boardName;
    }
}
