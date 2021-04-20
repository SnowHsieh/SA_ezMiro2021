package ntut.csie.sslab.kanban.usecase.workflow;

import ntut.csie.sslab.kanban.entity.model.workflow.Lane;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.lane.LaneDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertWorkflowsToDto {
    public static WorkflowDto transform(Workflow workflow){
        WorkflowDto dto = new WorkflowDto();
        dto.setWorkflowId(workflow.getWorkflowId());
        dto.setBoardId(workflow.getBoardId());
        dto.setName(workflow.getName());
        List<LaneDto> stageModels = new ArrayList<>();
        for(Lane stage : workflow.getStages()){
            LaneDto stageModel = ConvertLaneToDto.transform(stage);
            stageModels.add(stageModel);
        }
        dto.setLanes(stageModels);
        return dto;
    }


    public static List<WorkflowDto> transform(List<Workflow> workflows){
        List<WorkflowDto> workflowDtos = new ArrayList<>();
        for(Workflow workflow : workflows) {
            workflowDtos.add(transform(workflow));
        }

        return workflowDtos;
    }
}
