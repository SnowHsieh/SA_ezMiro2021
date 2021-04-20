package ntut.csie.sslab.kanban.usecase.lane.get;

import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.Lane;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.workflow.ConvertLaneToDto;
import ntut.csie.sslab.kanban.usecase.lane.LaneDto;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

import java.util.Optional;

public class GetLaneUseCaseImpl implements GetLaneUseCase, GetLaneInput {
    private WorkflowRepository workflowRepository;
    private String workflowId;
    private String laneId;

    public GetLaneUseCaseImpl(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Override
    public void execute(GetLaneInput input, GetLaneOutput output) {

        Workflow workflow= workflowRepository.findById(input.getWorkflowId()).orElse(null);
        if (null == workflow){
            output.setExitCode(ExitCode.FAILURE)
                   .setMessage("Get lane failed: workflow not found, workflow id = " + input.getWorkflowId());
            return;
        }

        Optional<Lane> lane = workflow.getLaneById(input.getLaneId());
        if (!lane.isPresent()){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage("Get lane failed: lane not found, lane id = " + input.getLaneId());
            return;
        }

        LaneDto laneDto = ConvertLaneToDto.transform(lane.get());
        output.setLaneModel(laneDto);
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String getLaneId() {
        return laneId;
    }

    @Override
    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }
}
