package ntut.csie.sslab.team.entity.model.team;

import ntut.csie.sslab.ddd.model.Entity;

public class Board extends Entity<BoardId> {

    private TeamId teamId;
    private ProjectId projectId;
    private String name;

    public Board(BoardId id, TeamId teamId, ProjectId projectId, String name) {
       super(id);
       this.name = name;
       this.teamId = teamId;
       this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public void setProjectId(ProjectId projectId) {
        this.projectId = projectId;
    }
}
