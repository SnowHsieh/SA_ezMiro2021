package ntut.csie.sslab.team.entity.model.team.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.team.entity.model.team.TeamId;


public class TeamDeleted extends DomainEvent {
    private final TeamId teamId;

    public TeamDeleted(TeamId id) {
        super(DateProvider.now());
        teamId = id;
    }

    public TeamId teamId() {
        return teamId;
    }
}
