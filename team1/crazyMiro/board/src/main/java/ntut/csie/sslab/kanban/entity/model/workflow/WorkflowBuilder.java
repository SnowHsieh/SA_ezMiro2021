package ntut.csie.sslab.kanban.entity.model.workflow;

import java.util.UUID;

public class WorkflowBuilder {

    private String id;
    private String boardId;
    private String name;
    private String userId;
    private String username;

    public static WorkflowBuilder newInstance() {
        return new WorkflowBuilder();
    }

    public WorkflowBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public WorkflowBuilder name(String name) {
        this.name = name;
        return this;
    }

    public WorkflowBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public WorkflowBuilder username(String username) {
        this.username = username;
        return this;
    }

    public Workflow build() {
        id = UUID.randomUUID().toString();
        Workflow workflow = new Workflow(id, boardId, name, userId, username);
        return workflow;
    }
}
