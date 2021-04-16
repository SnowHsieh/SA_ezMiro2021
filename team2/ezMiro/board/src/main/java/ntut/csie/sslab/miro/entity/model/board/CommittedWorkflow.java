package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class CommittedWorkflow extends ValueObject {
    private String boardId;
    private String workflowId;
    private int order;

    public CommittedWorkflow(String boardId, String workflowId, int order) {
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.order = order;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
