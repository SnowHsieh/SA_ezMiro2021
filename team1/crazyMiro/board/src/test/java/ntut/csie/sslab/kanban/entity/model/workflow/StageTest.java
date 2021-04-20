package ntut.csie.sslab.kanban.entity.model.workflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StageTest {

    private Stage rootStage;
    private SwimLane swimLane;

    @BeforeEach
    public void setUp() {

        rootStage = new Stage(
                "stage id",
                "workflow id",
                NullLane.newInstance(),
                "To Do",
                WipLimit.valueOf(-1),
                0,
                LaneType.Standard);

        swimLane = new SwimLane(
                "stage id",
                "workflow id",
                rootStage,
                "To Do",
                WipLimit.valueOf(-1),
                0,
                LaneType.Standard);
    }

    @Test
    public void test_a_stage_is_a_stage () {
        assertTrue(rootStage.isStage());
    }

    @Test
    public void test_a_swimlane_is_not_a_stage () {
        assertFalse(swimLane.isStage());
    }

    @Test
    public void test_a_stage_is_a_root_stage () {
        assertTrue(rootStage.isRoot());
    }

    @Test
    public void test_a_stage_is_a_not_a_root_stage () {

        Stage substage = new Stage(
                "stage id",
                "workflow id",
                rootStage,
                "To Do",
                WipLimit.valueOf(-1),
                0,
                LaneType.Standard);

        assertFalse(substage.isRoot());
    }

    @Test
    public void test_a_swimlane_is_a_not_a_root_stage () {
        assertFalse(swimLane.isRoot());
    }

}
