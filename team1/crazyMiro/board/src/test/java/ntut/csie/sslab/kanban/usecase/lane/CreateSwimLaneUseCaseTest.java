package ntut.csie.sslab.kanban.usecase.lane;

import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CreateSwimLaneUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();

        teamId = "team id for create swimlane";
        userId = "userId";
    }

    @Test
    public void should_succeed_when_create_swimLane() {

        String boardId = createBoard(teamId, "board name", userId);
        String workflowId = createWorkflow(boardId, "workflow name", userId, username);
        Workflow workflow = workflowRepository.findById(workflowId).get();
        assertNotNull(workflow.getWorkflowId());
        assertEquals(workflowId, workflow.getWorkflowId());

        String todoStageId = createStage(boardId, userId, username,workflowId, "-1", "todo", 2, "Standard");

        workflow = workflowRepository.findById(workflowId).get();
        assertNotNull(todoStageId);
        assertEquals(1 , workflow.getStages().size());

        String subSwimLaneId = createSwimLane(workflowId, todoStageId, "subSwimLane", 2, "Standard", userId, username, boardId);

        workflow = workflowRepository.findById(workflowId).get();
        assertEquals(1, workflow.getStages().size());
        assertNotNull(subSwimLaneId);
        assertEquals(1, workflow.getStages().get(0).getChildren().size());
    }
}
