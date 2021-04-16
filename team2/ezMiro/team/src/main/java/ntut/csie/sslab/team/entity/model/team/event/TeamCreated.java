package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.TeamId;

public class TeamCreated extends DomainEvent {

    private final TeamId teamId;
    private final String userId;
    private final String teamName;

    public TeamCreated(TeamId teamId, String userId, String teamName) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.userId = userId;
        this.teamName = teamName;
    }

    public TeamId teamId() {
        return teamId;
    }

    public String userId() {
        return userId;
    }

    public String teamName() { return teamName; }
}
