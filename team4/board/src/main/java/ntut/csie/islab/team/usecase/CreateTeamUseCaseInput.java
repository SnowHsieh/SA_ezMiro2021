package ntut.csie.islab.team.usecase;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateTeamUseCaseInput extends Input {
    String getAdmin();

    void setAdmin(String username);

    String getTeamName();

    void setTeamName(String teamName);
}
