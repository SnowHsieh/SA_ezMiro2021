package ntut.csie.sslab.kanban.usecase.lane.stage.get;

import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.Lane;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.workflow.ConvertLaneToDto;
import ntut.csie.sslab.kanban.usecase.lane.LaneDto;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

import java.util.ArrayList;
import java.util.List;

public class GetStagesByWorkflowIdUseCaseImpl implements GetStagesByWorkflowIdUseCase , GetStagesByWorkflowIdInput{
    private String workflowId;
    private WorkflowRepository workflowRepository;

    public GetStagesByWorkflowIdUseCaseImpl(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Override
    public void execute(GetStagesByWorkflowIdInput input, GetStagesByWorkflowIdOutput output) {
        Workflow workflow= workflowRepository.findById(input.getWorkflowId()).orElse(null);
        if (null == workflow){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage("Get stage failed: workflow not found, workflow id = " + input.getWorkflowId());
            return;
        }
        List<LaneDto> stageModels=  new ArrayList<>();
        for(Lane stage: workflow.getStages()){
            LaneDto stageModel= ConvertLaneToDto.transform(stage);
            stageModels.add(stageModel);
        }
        output.setStageModels(stageModels);
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }


}
