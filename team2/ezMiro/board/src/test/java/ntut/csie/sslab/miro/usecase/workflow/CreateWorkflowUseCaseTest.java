package ntut.csie.sslab.miro.usecase.workflow;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.workflow.create.CreateWorkflowInput;
import ntut.csie.sslab.miro.usecase.workflow.create.CreateWorkflowUseCase;
import ntut.csie.sslab.miro.entity.model.workflow.Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CreateWorkflowUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        teamId = "team_id_for_create_workflow_use_case_" + DateProvider.now();
        userId = "user_id_888";
        username = "Jack";
        boardName = "DevOps board";
    }

    @Test
    public void should_succeed_when_create_workflow() {

        String boardId = createBoard(teamId, boardName, userId);
        Board board= boardRepository.findById(boardId).get();
        CreateWorkflowUseCase createWorkflowUseCase = newCreateWorkflowUseCase();

        CreateWorkflowInput createWorkflowInput = createWorkflowUseCase.newInput();
        CqrsCommandPresenter createWorkflowOutput = CqrsCommandPresenter.newInstance();

        createWorkflowInput.setWorkflowName("workflow");
        createWorkflowInput.setBoardId(boardId);
        createWorkflowInput.setUserId(userId);
        createWorkflowInput.setUsername(username);
        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        assertNotNull(createWorkflowOutput.getId());
        assertEquals(ExitCode.SUCCESS, createWorkflowOutput.getExitCode());

        Workflow workflow = workflowRepository.findById(createWorkflowOutput.getId()).get();
        assertEquals("workflow", workflow.getName());
        assertEquals(boardId, workflow.getBoardId());
        assertEquals(0, workflow.getStages().size());
    }
}
