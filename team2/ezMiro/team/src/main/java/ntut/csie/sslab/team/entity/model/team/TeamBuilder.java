package ntut.csie.sslab.team.entity.model.team;

import java.util.UUID;

public class TeamBuilder {
    private String userId;
    private String teamName;

    public static TeamBuilder newInstance() {
        return new TeamBuilder();
    }

    public TeamBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public TeamBuilder teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public Team build() {
        return Team.createTeamAndTeamAdminMember(UUID.randomUUID().toString(), teamName, userId);
    }
}
