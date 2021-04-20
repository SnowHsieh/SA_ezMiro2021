package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardCommitted extends DomainEvent{

    private final String cardId;
    private final String workflowId;
    private final String newLaneId;
    private final int order;
    private final String userId;
    private final String username;
    private final String boardId;

    public CardCommitted(String cardId, String workflowId, String newLaneId, int order, String userId, String username, String boardId) {
        super(DateProvider.now());
        this.cardId = cardId;
        this.workflowId = workflowId;
        this.newLaneId = newLaneId;
        this.order = order;
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

    public String newLaneId() {
        return newLaneId;
    }

    public int order() {return order;}

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
