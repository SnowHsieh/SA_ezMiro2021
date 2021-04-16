package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.BoardId;
import ntut.csie.sslab.team.entity.model.team.TeamId;

public class BoardRestored extends DomainEvent {
    private final TeamId teamId;
    private final BoardId boardId;

    public BoardRestored(TeamId teamId, BoardId boardId) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
    }

    public TeamId teamId() {
        return teamId;
    }

    public BoardId boardId() {
        return boardId;
    }
}
