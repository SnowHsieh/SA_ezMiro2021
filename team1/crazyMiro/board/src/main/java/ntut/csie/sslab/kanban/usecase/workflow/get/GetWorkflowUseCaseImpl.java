package ntut.csie.sslab.kanban.usecase.workflow.get;

import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.workflow.ConvertWorkflowsToDto;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

public class GetWorkflowUseCaseImpl implements GetWorkflowUseCase, GetWorkflowInput {
    private WorkflowRepository workflowRepository;
    private String workflowId;

    public GetWorkflowUseCaseImpl(WorkflowRepository workflowRepository){
        this.workflowRepository= workflowRepository;
    }
    @Override
    public void execute(GetWorkflowInput input, GetWorkflowOutput output) {
        Workflow workflow= workflowRepository.findById(input.getWorkflowId()).orElse(null);
        if (null == workflow){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage("Get workflow failed: workflow not found, workflow id = " + input.getWorkflowId());
            return;
        }
        output.setWorkflowDto(ConvertWorkflowsToDto.transform(workflow));
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId= workflowId;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }
}
