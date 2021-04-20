package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardUncommitted extends DomainEvent {

    private final String cardId;
    private final String workflowId;
    private final String laneId;
    private final String userId;
    private final String username;
    private final String boardId;

    public CardUncommitted(String cardId, String workflowId, String laneId, String userId, String username, String boardId) {
        super(DateProvider.now());
        this.cardId = cardId;
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
    }

    public String cardId() {
        return cardId;
    }

    public String workflowId() {
        return workflowId;
    }

    public String laneId() {
        return laneId;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String boardId() {
        return boardId;
    }
}
