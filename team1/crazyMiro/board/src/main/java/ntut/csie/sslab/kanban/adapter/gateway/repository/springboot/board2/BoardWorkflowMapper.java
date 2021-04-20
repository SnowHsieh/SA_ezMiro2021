package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import ntut.csie.sslab.kanban.entity.model.board2.CommittedWorkflow;

public class BoardWorkflowMapper {

    public CommittedWorkflowData transformToBoardWorkflowData(CommittedWorkflow committedWorkflow) {
        CommittedWorkflowData committedWorkflowData = new CommittedWorkflowData();
        committedWorkflowData.setWorkflowId(committedWorkflow.getWorkflowId());
        committedWorkflowData.setBoardId(committedWorkflow.getBoardId());
        committedWorkflowData.setOrder(committedWorkflow.getOrder());
        return committedWorkflowData;
    }
}
