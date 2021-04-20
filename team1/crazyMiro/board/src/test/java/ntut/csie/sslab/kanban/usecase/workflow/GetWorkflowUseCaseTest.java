package ntut.csie.sslab.kanban.usecase.workflow;

import ntut.csie.sslab.kanban.adapter.presenter.workflow.get.GetWorkflowPresenter;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.workflow.get.GetWorkflowInput;
import ntut.csie.sslab.kanban.usecase.workflow.get.GetWorkflowUseCase;
import ntut.csie.sslab.kanban.usecase.workflow.get.GetWorkflowUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class GetWorkflowUseCaseTest extends AbstractSpringBootJpaTest {


    @BeforeEach
    public void setUp() {
        super.setUp();

        boardId = "board id for get workflow by id";
        userId = "user id for get workflow by id";
        username = "Teddy";
    }

    @Test
    public void should_return_workflow_read_model_when_get_workflow_by_id() {

        String workflowId= createWorkflow(boardId, "workflow", userId, username);

        GetWorkflowUseCase getWorkflowUseCase = new GetWorkflowUseCaseImpl(workflowRepository);

        GetWorkflowInput input = (GetWorkflowInput) getWorkflowUseCase;
        GetWorkflowPresenter output = new GetWorkflowPresenter();

        input.setWorkflowId(workflowId);
        getWorkflowUseCase.execute(input, output);

        assertNotNull(output.getWorkflowDto());
        Assertions.assertEquals(workflowId,output.getWorkflowDto().getWorkflowId());
        Assertions.assertEquals(boardId,output.getWorkflowDto().getBoardId());
        Assertions.assertEquals("workflow",output.getWorkflowDto().getName());
        Assertions.assertEquals(0,output.getWorkflowDto().getLanes().size());
    }
}
