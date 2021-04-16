package ntut.csie.sslab.miro.adapter.presenter.workflow.get;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowDto;

public class WorkflowViewModel implements ViewModel {
    private WorkflowDto workflow;

    public WorkflowDto getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowDto workflow) {
        this.workflow = workflow;
    }
}
