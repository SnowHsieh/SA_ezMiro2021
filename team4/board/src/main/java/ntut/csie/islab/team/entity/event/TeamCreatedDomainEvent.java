package ntut.csie.islab.team.entity.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class TeamCreatedDomainEvent extends DomainEvent {
    private final String teamId;
    private final String boardId;
    public TeamCreatedDomainEvent(String id, String boardId) {
        super(DateProvider.now());
        this.teamId=id;
        this.boardId=boardId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBoardId() {
        return boardId;
    }
}
