package ntut.csie.sslab.miro.usecase.workflow.get;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowDto;

public interface GetWorkflowOutput extends Output {
    public void setWorkflowDto(WorkflowDto workflowDto);
    public WorkflowDto getWorkflowDto();
}
