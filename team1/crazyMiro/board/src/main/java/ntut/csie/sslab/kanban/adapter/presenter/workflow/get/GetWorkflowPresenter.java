package ntut.csie.sslab.kanban.adapter.presenter.workflow.get;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowDto;
import ntut.csie.sslab.kanban.usecase.workflow.get.GetWorkflowOutput;

public class GetWorkflowPresenter extends Result implements GetWorkflowOutput, Presenter<WorkflowViewModel> {

    private WorkflowDto workflowDto;


    @Override
    public WorkflowViewModel buildViewModel() {
        WorkflowViewModel viewModel = new WorkflowViewModel();
        viewModel.setWorkflow(workflowDto);
        return viewModel;
    }

    @Override
    public void setWorkflowDto(WorkflowDto workflowDto) {
        this.workflowDto = workflowDto;
    }

    @Override
    public WorkflowDto getWorkflowDto() {
        return workflowDto;
    }

}
