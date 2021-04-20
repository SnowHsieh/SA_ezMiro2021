package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

import java.util.Objects;

public class CardMoved extends DomainEvent {

    private final String workflowId;
    private final String cardId;
    private final String oldLaneId;
    private final String newLaneId;
    private final int order;
    private final String userId;
    private final String username;
    private final String boardId;

    public CardMoved(String workflowId, String cardId, String oldLaneId, String newLaneId, int order, String userId, String username, String boardId) {
        super(DateProvider.now());
        this.workflowId = workflowId;
        this.cardId = cardId;
        this.oldLaneId = oldLaneId;
        this.newLaneId = newLaneId;
        this.order = order;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
    }

    public String workflowId() { return workflowId; }

    public String cardId() {
        return cardId;
    }

    public String originalLaneId() {
        return oldLaneId;
    }

    public String newLaneId() {
        return newLaneId;
    }

    public int order() {
        return order;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardMoved)) return false;
        CardMoved that = (CardMoved) o;
        return Objects.equals(workflowId(), that.workflowId()) &&
                Objects.equals(cardId(), that.cardId()) &&
                Objects.equals(originalLaneId(), that.originalLaneId()) &&
                Objects.equals(newLaneId(), that.newLaneId()) &&
                Objects.equals(order(), that.order()) &&
                Objects.equals(userId(), that.userId()) &&
                Objects.equals(username(), that.username()) &&
                Objects.equals(boardId(), that.boardId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(workflowId(), cardId(), originalLaneId(), newLaneId(),  order(), userId(), username(), boardId());
    }

}
