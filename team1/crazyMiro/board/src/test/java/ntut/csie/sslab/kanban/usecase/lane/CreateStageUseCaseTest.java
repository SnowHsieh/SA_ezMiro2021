package ntut.csie.sslab.kanban.usecase.lane;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.lane.stage.create.CreateStageInput;
import ntut.csie.sslab.kanban.usecase.lane.stage.create.CreateStageUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CreateStageUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();

        teamId = "Team id for crate stage";
        boardName = "Board for create stage";
        userId = "User id for create stage";
        username = "Teddy Chen";
    }

    @Test
    public void should_succeed_when_create_root_stage() {

        String boardId = "board_id_for_create_stage";
        String workflowName = "first workflow";
        String workflowId = createWorkflow(boardId, workflowName, userId, username);

        Workflow workflow = workflowRepository.findById(workflowId).get();
        assertNotNull(workflow);
        assertEquals(workflowName, workflow.getName());
        assertEquals(0, workflow.getStages().size());

        CreateStageUseCase createStageUseCase = newCreateStageUseCase();
        CreateStageInput input = createStageUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setWorkflowId(workflowId);
        input.setParentId("-1");
        input.setStageName("todo");
        input.setWipLimit(2);
        input.setLaneType("Standard");
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        createStageUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        workflow = workflowRepository.findById(workflowId).get();
        assertEquals(1 , workflow.getStages().size());
        assertNotNull(workflow.getStages().get(0).getId(), output.getId());
        Assertions.assertEquals(1, workflowRepository.findById(workflowId).get().getStages().size());
    }


    // TODO add an integration test for eventual consistency
//    @Test
//    public void should_succeed_when_create_root_stage() {
//
//        String boardId = createBoard(teamId, boardName, userId);
//        String workflowId = createWorkflow(boardId, "first workflow", userId, username);
//
//        assertEquals(workflowId, boardRepository.getBoardById(boardId).getCommittedWorkflows().get(0).getWorkflowId());
//        assertEquals(1, boardRepository.getBoardById(boardId).getCommittedWorkflows().size());
//
//        Workflow workflow = workflowRepository.findById(workflowId).get();
//        assertNotNull(workflow);
//        assertEquals("work flow", workflow.getName());
//        assertEquals(0, workflow.getStages().size());
//
//        CreateStageUseCase createStageUseCase = newCreateStageUseCase();
//        CreateStageInput createStageInput = (CreateStageInput) createStageUseCase;
//        CreateStagePresenter createStageOutput = new CreateStagePresenter();
//
//        createStageInput.setWorkflowId(workflowId);
//        createStageInput.setParentId("-1");
//        createStageInput.setStageName("todo");
//        createStageInput.setWipLimit(2);
//        createStageInput.setLaneType("Standard");
//        createStageInput.setUserId(userId);
//        createStageInput.setUsername(username);
//        createStageInput.setBoardId(boardId);
//        createStageUseCase.execute(createStageInput, createStageOutput);
//
//
//        workflow = workflowRepository.findById(workflowId).get();
//        assertEquals(1 , workflow.getStages().size());
//        assertNotNull(workflow.getStages().get(0).getLaneId(), createStageOutput.getStageId());
//        assertEquals(1, workflowRepository.findById(workflowId).get().getStages().size());
//    }

//    @Test
//    public void should_succeed_when_create_stage_in_swimLane() {
//        String workflowId = utility.createWorkflow(boardId, "work flow", userId, username);
//        assertEquals(workflowId, boardRepository.getBoardById(boardId).getCommittedWorkflows().get(0).getWorkflowId());
//        assertEquals(1, boardRepository.getBoardById(boardId).getCommittedWorkflows().size());
//
//        Workflow workflow = workflowRepository.findById(workflowId).get();
//
//        assertNotNull(workflow);
//        assertEquals("work flow", workflow.getName());
//        assertEquals(0, workflow.getStages().size());
//
//        String rootStageId = utility.createStage(boardId, userId, username, workflowId, "-1","todo", 2, "Standard");
//
//        workflow = workflowRepository.findById(workflowId).get();
//        assertNotNull(rootStageId);
//        assertEquals(1 , workflow.getStages().size());
//
//
//        String swimLaneId = utility.createSwimLane(workflowId, rootStageId, "swimLane", 2, "Standard", userId, username, boardId);
//        workflow = workflowRepository.findById(workflowId).get();
//        assertNotNull(swimLaneId);
//        assertEquals(1, workflow.getLaneById(rootStageId).getLanes().size());
//        assertEquals("swimLane", workflow.getLaneById(swimLaneId).getTitle());
//
//        String subStageId = utility.createStage(boardId, userId, username,workflowId, swimLaneId, "subStage", 2, "Standard");
//
//        workflow = workflowRepository.findById(workflowId).get();
//        assertNotNull(subStageId);
//        assertEquals(1, workflow.getLaneById(swimLaneId).getLanes().size());
//        assertEquals("subStage", workflow.getLaneById(subStageId).getTitle());
//
//    }
}
