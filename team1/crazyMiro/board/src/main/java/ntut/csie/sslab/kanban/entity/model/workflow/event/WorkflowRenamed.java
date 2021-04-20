package ntut.csie.sslab.kanban.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class WorkflowRenamed extends DomainEvent {
    private final String workflowId;
    private final String oldName;
    private final String newName;
    private final String userId;
    private final String username;
    private final String boardId;

    public WorkflowRenamed(String workflowId, String oldName, String newName, String userId, String username, String boardId){
        super(DateProvider.now());
        this.workflowId = workflowId;
        this.oldName = oldName;
        this.newName = newName;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
    }

    public String workflowId() {
        return workflowId;
    }

    public String oldName() {
        return oldName;
    }

    public String newName() {
        return newName;
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
