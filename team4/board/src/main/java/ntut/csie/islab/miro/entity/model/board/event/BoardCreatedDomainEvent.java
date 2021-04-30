package ntut.csie.islab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.UUID;

public class BoardCreatedDomainEvent extends DomainEvent {
     private final UUID teamId;
     private final UUID boardId;

    public BoardCreatedDomainEvent(UUID teamId, UUID boardId) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public UUID getBoardId() {
        return boardId;
    }
}
