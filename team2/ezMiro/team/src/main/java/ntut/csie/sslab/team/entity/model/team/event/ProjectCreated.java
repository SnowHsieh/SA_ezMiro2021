package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.ProjectId;
import ntut.csie.sslab.team.entity.model.team.TeamId;

import java.util.Date;

public class ProjectCreated extends DomainEvent {

    private final TeamId teamId;
    private final ProjectId projectId;
    private final String name;

    public ProjectCreated(TeamId teamId, ProjectId projectId, String name) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.projectId = projectId;
        this.name = name;
    }

    public TeamId teamId() { return teamId; }

    public ProjectId projectId() {
        return projectId;
    }

    public String name() {
        return name;
    }
}
