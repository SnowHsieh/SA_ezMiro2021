package ntut.csie.sslab.kanban.usecase.lane;

import ntut.csie.sslab.kanban.adapter.presenter.lane.get.GetLanePresenter;
import ntut.csie.sslab.kanban.entity.model.workflow.LaneType;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.lane.get.GetLaneInput;
import ntut.csie.sslab.kanban.usecase.lane.get.GetLaneUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class GetLaneUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp()  {
        super.setUp();

        teamId = "team id for get lane by id";
        boardId = "board id for get lane by id";
        userId = "user id for get lane by id";
        username = "Teddy";

    }


    // TODO 應該回傳workflow而非lane
    // 原本的設計為什麼需要回傳 lane?
    @Test
    public void should_return_lane_model_when_get_lane_by_id(){
        String workflowId = createWorkflow(boardId, "work flow", userId, username);
        createStage(boardId, userId, username, workflowId, "-1", "firstStage", 6, LaneType.Standard.toString());

        Assertions.assertEquals(1, workflowRepository.findById(workflowId).get().getStages().size());

        Workflow workflow=  workflowRepository.findById(workflowId).get();
        String stageId = workflow.getStages().get(0).getId();

        GetLaneUseCase getLaneUseCase = newGetLaneByIdUseCase();

        GetLaneInput input = (GetLaneInput) getLaneUseCase;
        input.setWorkflowId(workflowId);
        input.setLaneId(stageId);

        GetLanePresenter output = new GetLanePresenter();

        getLaneUseCase.execute(input, output);

        Assertions.assertEquals("firstStage", output.getLaneModel().getName());
        assertNotNull(stageId);
        Assertions.assertEquals(stageId, output.getLaneModel().getLaneId());
        Assertions.assertEquals(6, output.getLaneModel().getWipLimit());
        Assertions.assertEquals("Standard", output.getLaneModel().getType());
    }
}
