package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.ProjectId;
import ntut.csie.sslab.team.entity.model.team.TeamId;

import java.util.Date;

public class ProjectRenamed extends DomainEvent {
    private final TeamId teamId;
    private final ProjectId projectId;
    private final String oldName;
    private final String newName;

    public ProjectRenamed(TeamId teamId, ProjectId projectId, String oldName, String newName) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.projectId = projectId;
        this.oldName = oldName;
        this.newName = newName;
    }

    public TeamId teamId() {
        return teamId;
    }

    public ProjectId projectId() {
        return projectId;
    }

    public String oldName() {
        return oldName;
    }

    public String newName() {
        return newName;
    }
}
