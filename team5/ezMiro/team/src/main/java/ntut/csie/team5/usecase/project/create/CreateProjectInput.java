package ntut.csie.team5.usecase.project.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateProjectInput extends Input {

    String getTeamId();

    void setTeamId(String teamId);

    String getProjectName();

    void setProjectName(String projectName);
}
