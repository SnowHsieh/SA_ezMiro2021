package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class WorkflowDeleted extends DomainEvent {

    private final String workflowId;
    private final String workflowName;
    private final String userId;
    private final String username;
    private final String boardId;

    public WorkflowDeleted(String workflowId, String workflowName, String userId, String username, String boardId) {
        super(DateProvider.now());
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
    }

    public String workflowId() {
        return workflowId;
    }

    public String workflowName() { return workflowName; }

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
