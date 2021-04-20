package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import ntut.csie.sslab.kanban.entity.model.workflow.LaneType;
import ntut.csie.sslab.kanban.entity.model.workflow.NullLane;
import ntut.csie.sslab.kanban.entity.model.workflow.Stage;
import ntut.csie.sslab.kanban.entity.model.workflow.WipLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LaneMapperTest {

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void laneMapper_test() {
        Stage rootStage1 = new Stage("stage1","workflowId", NullLane.newInstance(),"root stage 1",new WipLimit(-1),0, LaneType.Standard);
        LaneData laneData = LaneMapper.transformToLaneData(rootStage1);
        assertEquals(rootStage1.getId(), laneData.getId());

        rootStage1.createStage("substage1", -1, LaneType.Standard);
        rootStage1.createSwimLane("swimlnae1", -1, LaneType.Standard);

        laneData = LaneMapper.transformToLaneData(rootStage1);
        assertEquals(rootStage1.getId(), laneData.getId());


    }

}
