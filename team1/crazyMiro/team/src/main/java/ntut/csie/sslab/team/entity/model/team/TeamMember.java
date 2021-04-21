package ntut.csie.sslab.team.entity.model.team;

import ntut.csie.sslab.ddd.model.ValueObject;

public class TeamMember extends ValueObject {
    private String userId;
    private TeamId teamId;
    private Role role;

    public TeamMember(String userId, TeamId teamId, Role role) {
        this.userId = userId;
        this.teamId = teamId;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public TeamId getTeamId() {
        return teamId;
    }

    public Role getRole() {
        return role;
    }
}
