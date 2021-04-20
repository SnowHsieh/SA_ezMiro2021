package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="committed_workflow")
public class CommittedWorkflowData {

    @Column(name="board_id", nullable = false)
    private String boardId;

    @Id
    @Column(name="workflow_id")
    private String workflowId;

    @Column(name="workflow_order")
    private int order;

    public CommittedWorkflowData(){}

    public CommittedWorkflowData(String boardId, String workflowId, int order) {
        this();
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
