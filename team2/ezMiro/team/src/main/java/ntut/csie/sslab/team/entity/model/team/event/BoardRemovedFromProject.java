package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.Board;
import ntut.csie.sslab.team.entity.model.team.BoardId;
import ntut.csie.sslab.team.entity.model.team.ProjectId;
import ntut.csie.sslab.team.entity.model.team.TeamId;

import java.util.Date;

public class BoardRemovedFromProject extends DomainEvent {

    private final TeamId teamId;
    private final BoardId boardId;
    private final ProjectId projectId;
    private final String boardName;

    public BoardRemovedFromProject(TeamId teamId, BoardId boardId, ProjectId projectId, String boardName) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
        this.projectId = projectId;
        this.boardName = boardName;
    }

    public TeamId teamId() {
        return teamId;
    }

    public BoardId boardId() {
        return boardId;
    }

    public ProjectId projectId() {
        return projectId;
    }

    public String boardName() {
        return boardName;
    }
}
