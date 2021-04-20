package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import ntut.csie.sslab.kanban.entity.model.board2.CommittedWorkflow;

import java.util.ArrayList;
import java.util.List;

public class CommittedWorkflowMapper {

    public static CommittedWorkflowData transformToData(CommittedWorkflow committedWorkflow){
        CommittedWorkflowData data = new CommittedWorkflowData(
                committedWorkflow.getBoardId(),
                committedWorkflow.getWorkflowId(),
                committedWorkflow.getOrder()
        );
        return data;
    }

    public static List<CommittedWorkflowData> transformToData(List<CommittedWorkflow> committedWorkflows){
        List<CommittedWorkflowData> datas = new ArrayList<>();
        committedWorkflows.forEach( x -> datas.add(transformToData(x)));
        return datas;
    }

}
