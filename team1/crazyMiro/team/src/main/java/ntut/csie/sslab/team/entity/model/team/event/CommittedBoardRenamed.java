package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.BoardId;
import ntut.csie.sslab.team.entity.model.team.TeamId;

public class CommittedBoardRenamed extends DomainEvent {

    private final TeamId teamId;
    private final BoardId boardId;
    private final String oldName;
    private final String newName;

    public CommittedBoardRenamed(TeamId teamId, BoardId boardId, String oldName, String newName) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
        this.oldName = oldName;
        this.newName = newName;
    }

    public TeamId teamId() {
        return teamId;
    }

    public BoardId boardId() {
        return boardId;
    }

    public String oldName() {
        return oldName;
    }

    public String newName() {
        return newName;
    }
}
