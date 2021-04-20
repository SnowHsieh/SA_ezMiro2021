package ntut.csie.sslab.kanban.adapter.presenter.workflow.get;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowDto;

public class WorkflowViewModel implements ViewModel {
    private WorkflowDto workflow;

    public WorkflowDto getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowDto workflow) {
        this.workflow = workflow;
    }
}
