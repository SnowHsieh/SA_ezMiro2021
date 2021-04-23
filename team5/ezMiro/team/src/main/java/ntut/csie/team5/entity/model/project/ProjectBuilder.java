package ntut.csie.team5.entity.model.project;

import java.util.UUID;

public class ProjectBuilder {

    private String projectId;
    private String name;
    private String teamId;

    public static ProjectBuilder newInstance() {
        return new ProjectBuilder();
    }

    public ProjectBuilder teamId(String teamId) {
        this.teamId = teamId;
        return this;
    }

    public ProjectBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Project build() {
        projectId = UUID.randomUUID().toString();
        Project project = new Project(projectId, name, teamId);
        return project;
    }
}
