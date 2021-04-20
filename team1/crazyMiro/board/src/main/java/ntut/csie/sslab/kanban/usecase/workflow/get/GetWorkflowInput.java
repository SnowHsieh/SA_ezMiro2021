package ntut.csie.sslab.kanban.usecase.workflow.get;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetWorkflowInput extends Input {
    public void setWorkflowId(String workflowId);
    public String getWorkflowId();
}
