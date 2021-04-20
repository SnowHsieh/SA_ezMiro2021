package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class WipLimitSet extends DomainEvent {

    private final String workflowId;
    private final String laneId;
    private final int oldWipLimit;
    private final int newWipLimit;
    private final String userId;
    private final String username;
    private final String boardId;
    private final String layout;

    public WipLimitSet(String workflowId,
                       String laneId,
                       int oldWipLimit,
                       int newWipLimit,
                       String userId,
                       String username,
                       String boardId,
                       String layout) {
        super(DateProvider.now());
        this.workflowId = workflowId;
        this.laneId = laneId;
        this.oldWipLimit = oldWipLimit;
        this.newWipLimit = newWipLimit;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
        this.layout = layout;
    }

    public String workflowId() {
        return workflowId;
    }

    public String laneId() {
        return laneId;
    }

    public int oldWipLimit() {
        return oldWipLimit;
    }

    public int newWipLimit() {
        return newWipLimit;
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

    public String layout() {
        return layout;
    }
}
