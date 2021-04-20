package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.workflow.get;

import ntut.csie.sslab.kanban.adapter.presenter.workflow.get.WorkflowViewModel;
import ntut.csie.sslab.kanban.adapter.presenter.workflow.get.GetWorkflowPresenter;
import ntut.csie.sslab.kanban.usecase.workflow.get.GetWorkflowInput;
import ntut.csie.sslab.kanban.usecase.workflow.get.GetWorkflowUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
public class GetWorkflowController {
        private GetWorkflowUseCase getWorkflowUseCase;

    @Autowired
    public void setGetWorkflowUseCase(GetWorkflowUseCase getWorkflowUseCase) {
        this.getWorkflowUseCase = getWorkflowUseCase;
    }

        @GetMapping(path = "${KANBAN_PREFIX}/workflows/{workflowId}", produces = "application/json")
        public WorkflowViewModel getWorkflow(@PathVariable("workflowId") String workflowId, @QueryParam("boardId") String boardId) {

            GetWorkflowInput input = (GetWorkflowInput) getWorkflowUseCase;
            input.setWorkflowId(workflowId);

            GetWorkflowPresenter presenter = new GetWorkflowPresenter();

            getWorkflowUseCase.execute(input, presenter);

            return presenter.buildViewModel();
        }
}
