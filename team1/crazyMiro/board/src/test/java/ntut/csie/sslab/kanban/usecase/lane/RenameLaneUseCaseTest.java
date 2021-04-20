package ntut.csie.sslab.kanban.usecase.lane;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.LaneType;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.lane.rename.RenameLaneInput;
import ntut.csie.sslab.kanban.usecase.lane.rename.RenameLaneUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RenameLaneUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();

        teamId = "Team id for rename lane";
        userId = "user id for rename lane";
        username = "Teddy";
        boardId = "Board id for rename lane";
    }

    @Test
    public void should_succeed_when_rename_stage() {

        String workflowNAme = "workflow name for rename lane";
        String workflowId= createWorkflow(boardId, workflowNAme, userId, username);
        String stageId = createStage(boardId, userId, username, workflowId, "-1", "rootStage", -1, LaneType.Standard.toString());

        String newStageName = "new Stage Name";

        RenameLaneUseCase renameLaneUseCase = newRenameLaneUseCase();

        RenameLaneInput input = renameLaneUseCase.newInput();
        input.setWorkflowId(workflowId);
        input.setLaneId(stageId);
        input.setNewName(newStageName);
        input.setUserId(userId);
        input.setUsername(username);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        renameLaneUseCase.execute(input, output);
        renameLaneUseCase.execute(input, output);

        assertEquals(stageId, output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Workflow workflow = workflowRepository.findById(workflowId).get();

        assertEquals(newStageName, workflow.getLaneById(stageId).get().getName());

    }

    @Test
    public void should_succeed_when_rename_swimlane(){

        String workflowId= createWorkflow(boardId, "workflow", userId, username);

        String stageId = createStage(boardId, userId, username, workflowId, "-1", "rootStage", -1, LaneType.Standard.toString());
        String swimLaneId  = createSwimLane(workflowId, stageId, "swimLaneTitle", -1, LaneType.Standard.toString(), userId, username, boardId);
        String newSwimlaneName = "new swimLane title";
        String laneId = renameLane(workflowId, swimLaneId, newSwimlaneName, userId, username);

        assertEquals(swimLaneId, laneId);
        Workflow workflow = workflowRepository.findById(workflowId).get();
        assertEquals(newSwimlaneName, workflow.getLaneById(swimLaneId).get().getName());
    }

}
