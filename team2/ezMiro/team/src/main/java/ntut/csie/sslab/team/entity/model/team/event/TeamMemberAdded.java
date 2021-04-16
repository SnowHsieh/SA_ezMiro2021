package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.TeamId;

import java.util.Date;

public class TeamMemberAdded extends DomainEvent {

    private final String userId;
    private final TeamId teamId;
    private final String role;

    public TeamMemberAdded(String userId, TeamId teamId, String role) {
        super(DateProvider.now());
        this.userId = userId;
        this.teamId = teamId;
        this.role = role;
    }

    public String userId() {
        return userId;
    }

    public TeamId teamId() {
        return teamId;
    }

    public String role() {
        return role;
    }
}
