package ntut.csie.sslab.kanban.entity.model.board2.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class WorkflowCommitted extends DomainEvent {

    private final String boardId;
    private final String workflowId;

    public WorkflowCommitted(String boardId, String workflowId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.workflowId = workflowId;
    }

    public String boardId() { return boardId; }

    public String workflowId() { return workflowId; }
}
