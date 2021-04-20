package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import ntut.csie.sslab.kanban.entity.model.workflow.Lane;
import ntut.csie.sslab.kanban.entity.model.workflow.Stage;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkflowMapper {

    public static WorkflowData transformToData(Workflow workflow) {

        WorkflowData workflowData = new WorkflowData(workflow.getId(), workflow.getBoardId(), workflow.getName());

        List<LaneData> laneData = new ArrayList<>();
        for(Lane stage : workflow.getStages()) {
            laneData.add(LaneMapper.transformToLaneData(stage));
        }
        laneData.forEach(workflowData::addLaneData);

        return workflowData;
    }

    public static List<WorkflowData> transformToData(List<Workflow> workflows) {
        List<WorkflowData> result = new ArrayList<>();
        workflows.forEach( x -> result.add(transformToData(x)));
        return result;
    }


    public static Workflow transformToDomain(WorkflowData workflowData) {
        Workflow workflow = new Workflow(
                workflowData.getId(),
                workflowData.getBoardId(),
                workflowData.getName(),
                "",
                "");

        List<Lane> lanes = new ArrayList<>();
        for(LaneData laneData : workflowData.getLaneDatas()) {
            lanes.add(LaneMapper.transformToModel(laneData));
        }

        for (Lane each : lanes) {
            workflow.addStage((Stage)each);
        }
        workflow.clearDomainEvents();
        return workflow;
    }

    public static List<Workflow> transformToDomain(List<WorkflowData> workflowDatas) {
        List<Workflow> result = new ArrayList<>();
        workflowDatas.forEach( x -> result.add(transformToDomain(x)));
        return result;
    }

}
