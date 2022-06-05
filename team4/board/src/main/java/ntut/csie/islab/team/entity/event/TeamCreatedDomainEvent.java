package ntut.csie.islab.team.entity.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class TeamCreatedDomainEvent extends DomainEvent {
    private final String teamId;
    private final String boardId;
    private final String teamName;

    public TeamCreatedDomainEvent(String id, String boardId, String teamName) {
        super(DateProvider.now());
        this.teamId = id;
        this.boardId = boardId;
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getTeamName() {
        return teamName;
    }
}
