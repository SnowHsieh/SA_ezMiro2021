package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.BoardId;
import ntut.csie.sslab.team.entity.model.team.TeamId;

import java.util.Date;

public class BoardCommittedToTeam extends DomainEvent {
    private final TeamId teamId;
    private final BoardId boardId;
    private final String name;

    public BoardCommittedToTeam(TeamId teamId, BoardId boardId, String name) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
        this.name = name;
    }

    public BoardId boardId() {
        return boardId;
    }

    public TeamId teamId() {
        return teamId;
    }

    public String name() {
        return name;
    }
}
