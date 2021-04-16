package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.ProjectId;
import ntut.csie.sslab.team.entity.model.team.TeamId;

import java.util.Date;

public class ProjectDeleted extends DomainEvent {
    private final TeamId teamId;
    private final ProjectId projectId;

    public ProjectDeleted(TeamId id, ProjectId projectId) {
        super(DateProvider.now());
        this.teamId = id;
        this.projectId = projectId;
    }

    public TeamId teamId() {
        return teamId;
    }

    public ProjectId projectId() {
        return projectId;
    }
}
