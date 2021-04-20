package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.workflow.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.workflow.create.CreateWorkflowInput;
import ntut.csie.sslab.kanban.usecase.workflow.create.CreateWorkflowUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
@RestController
public class CreateWorkflowController {
        private CreateWorkflowUseCase createWorkflowUseCase;

        @Autowired
        public void setCreateWorkflowUseCase(CreateWorkflowUseCase createWorkflowUseCase) {
            this.createWorkflowUseCase = createWorkflowUseCase;
        }

        @PostMapping(path = "${KANBAN_PREFIX}/workflows", consumes = "application/json", produces = "application/json")
        public CqrsCommandViewModel createWorkflow(@QueryParam("boardId") String boardId,
                                                   @QueryParam("userId") String userId,
                                                   @QueryParam("username") String username,
                                                   @RequestBody String workflowInfo) {

            String name = "";
            try {
                JSONObject workflowJSON = new JSONObject(workflowInfo);
                name = workflowJSON.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CreateWorkflowInput input = createWorkflowUseCase.newInput();
            input.setBoardId(boardId);
            input.setWorkflowName(name);
            input.setUserId(userId);
            input.setUsername(username);

            CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

            createWorkflowUseCase.execute(input, presenter);

            return presenter.buildViewModel();
        }
}
