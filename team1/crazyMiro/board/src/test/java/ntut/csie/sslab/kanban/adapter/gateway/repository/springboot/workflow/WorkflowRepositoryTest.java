package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.workflow.*;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkflowRepositoryTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp()  {
        super.setUp();

    }

    @Test
    public void test_lane_tree_structure_should_succeed(){
        Workflow workflow1 = new Workflow("w1" + DateProvider.now(), "boardId", "workflow1Name", "userId", "username");
        Workflow workflow2 = new Workflow("w2" + DateProvider.now(), "boardId", "workflow2Name", "userId", "username");

        WipLimit wipLimit = new WipLimit(-1);
        Stage rootStage1 = new Stage("rootStage1" + DateProvider.now(), workflow1.getWorkflowId(), NullLane.newInstance(), "rootstage1", wipLimit, 0, LaneType.Standard);
        Stage rootStage2 = new Stage("rootStage2"+ DateProvider.now(), workflow1.getWorkflowId(), NullLane.newInstance(), "rootstage2", wipLimit, 1, LaneType.Standard);

        Lane stage3 = rootStage1.createStage("stage3", wipLimit.getValue(), LaneType.Standard);
        Lane stage4 = rootStage1.createStage("stage4", wipLimit.getValue(), LaneType.Standard);

        Lane swimLane1 = rootStage2.createSwimLane("swimLane1", wipLimit.getValue(), LaneType.Standard);
        Lane swimLane2 = rootStage2.createSwimLane("swimLane2", wipLimit.getValue(),LaneType.Standard);

        Lane stage5 = swimLane1.createStage("stage5", wipLimit.getValue(), LaneType.Standard);
        Lane stage6 = swimLane1.createStage("stage6", wipLimit.getValue(), LaneType.Standard);

        workflow1.addStage(rootStage1);
        workflow1.addStage(rootStage2);
        workflowRepository.save(workflow1);
        workflowRepository.save(workflow2);
        workflow1 = workflowRepository.findById(workflow1.getId()).get();
        workflow2 = workflowRepository.findById(workflow2.getId()).get();

        assertEquals(2, workflow1.getStages().size());
        assertEquals(NullLane.ID, workflow1.getLaneById(rootStage1.getId()).get().getParent().getId());
        assertEquals(NullLane.ID, workflow1.getLaneById(rootStage2.getId()).get().getParent().getId());
        assertEquals(2, workflow1.getLaneById(rootStage1.getId()).get().getChildren().size());
        assertEquals(rootStage1.getId(), workflow1.getLaneById(stage3.getId()).get().getParent().getId());
        assertEquals(rootStage1.getId(), workflow1.getLaneById(stage4.getId()).get().getParent().getId());
        assertEquals(2, workflow1.getLaneById(rootStage2.getId()).get().getChildren().size());
        assertEquals(rootStage2.getId(), workflow1.getLaneById(swimLane1.getId()).get().getParent().getId());
        assertEquals(rootStage2.getId(), workflow1.getLaneById(swimLane2.getId()).get().getParent().getId());
        assertEquals(2, workflow1.getLaneById(swimLane1.getId()).get().getChildren().size());
        assertEquals(swimLane1.getId(), workflow1.getLaneById(stage5.getId()).get().getParent().getId());
        assertEquals(swimLane1.getId(), workflow1.getLaneById(stage6.getId()).get().getParent().getId());

        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(rootStage1.getId()).get().getWorkflowId());
        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(rootStage2.getId()).get().getWorkflowId());

        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(stage3.getId()).get().getWorkflowId());
        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(stage4.getId()).get().getWorkflowId());
        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(stage5.getId()).get().getWorkflowId());
        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(stage6.getId()).get().getWorkflowId());
        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(swimLane1.getId()).get().getWorkflowId());
        assertEquals(workflow1.getWorkflowId(), workflow1.getLaneById(swimLane2.getId()).get().getWorkflowId());

        assertEquals(0, workflow2.getStages().size());

    }

    @Test
    public void test_rename_root_stage_should_succeed(){

        Workflow workflow = new Workflow(UUID.randomUUID().toString(), "boardId", "workflowName", "userId", "username");

        WipLimit wipLimit = new WipLimit(-1);
        Stage rootStage1 = new Stage(UUID.randomUUID().toString(), workflow.getWorkflowId(), NullLane.newInstance(), "rootstage1", wipLimit, 0, LaneType.Standard);
        workflow.addStage(rootStage1);
        workflowRepository.save(workflow);

        workflow = workflowRepository.findById(workflow.getId()).get();
        workflow.renameLane(rootStage1.getId(), "my new name", userId, username);
        workflowRepository.save(workflow);
    }

    @Test
    public void test_delete_workflow_and_a_root_stage_should_succeed(){

        Workflow workflow = new Workflow(UUID.randomUUID().toString(), "boardId", "workflowName_test_delete_workflow", "userId", "username");

        WipLimit wipLimit = new WipLimit(-1);
        Stage rootStage1 = new Stage(UUID.randomUUID().toString(), workflow.getWorkflowId(), NullLane.newInstance(), "rootstage1_test_delete_workflow", wipLimit, 0, LaneType.Standard);
        workflow.addStage(rootStage1);
        workflowRepository.save(workflow);

        workflow = workflowRepository.findById(workflow.getId()).get();
        workflowRepository.deleteById(workflow.getId());
    }

    @Test
    public void test_delete_root_stage_should_succeed(){

        Workflow workflow = new Workflow(UUID.randomUUID().toString(), "boardId", "workflowName", "userId", "username");

        WipLimit wipLimit = new WipLimit(-1);
        Stage rootStage1 = new Stage(UUID.randomUUID().toString(), workflow.getWorkflowId(), NullLane.newInstance(), "rootstage1_test_delete", wipLimit, 0, LaneType.Standard);
        workflow.addStage(rootStage1);
        workflowRepository.save(workflow);

        workflow = workflowRepository.findById(workflow.getId()).get();
        assertEquals(1, workflow.getStages().size());

        workflow.deleteLaneById(rootStage1.getId(), userId, username, boardId);
        assertEquals(0, workflow.getStages().size());
        workflowRepository.save(workflow);

        workflow = workflowRepository.findById(workflow.getId()).get();
        assertEquals(0, workflow.getStages().size());
    }

    @Test
    public void test_rename_substage_should_succeed(){

        Workflow workflow = new Workflow(UUID.randomUUID().toString(), "boardId", "workflowTitle", "userId", "username");

        WipLimit wipLimit = new WipLimit(-1);
        Stage rootStage1 = new Stage(UUID.randomUUID().toString() + DateProvider.now(), workflow.getWorkflowId(), NullLane.newInstance(), "rootstage1", wipLimit, 0, LaneType.Standard);
        Lane substage1 = rootStage1.createStage("substage1", wipLimit.getValue(), LaneType.Standard);
        workflow.addStage(rootStage1);
        workflowRepository.save(workflow);

        workflow = workflowRepository.findById(workflow.getId()).get();
        workflow.renameLane(substage1.getId(), "my new substage name", userId, username);
        workflowRepository.save(workflow);
    }


    @Test
    public void test_delete_substage_should_succeed(){

        Workflow workflow = new Workflow(UUID.randomUUID().toString(), "boardId", "workflowTitle", "userId", "username");

        WipLimit wipLimit = new WipLimit(-1);
        Stage rootStage1 = new Stage(UUID.randomUUID().toString() + DateProvider.now(), workflow.getWorkflowId(), NullLane.newInstance(), "rootstage1", wipLimit, 0, LaneType.Standard);
        Lane substage1 = rootStage1.createStage("substage1", wipLimit.getValue(), LaneType.Standard);
        workflow.addStage(rootStage1);

        assertEquals(1, workflow.getStages().size());
        assertEquals(1, workflow.getStages().get(0).getChildren().size());
        workflowRepository.save(workflow);

        workflow = workflowRepository.findById(workflow.getId()).get();
        workflow.deleteLaneById(substage1.getId(), userId, username, boardId);
        workflowRepository.save(workflow);

        assertEquals(1, workflow.getStages().size());
        assertEquals(0, workflow.getStages().get(0).getChildren().size());
    }

    @Test
    public void test_delete_swimlane_should_succeed(){

        Workflow workflow = new Workflow(UUID.randomUUID().toString(), "boardId", "workflowTitle", "userId", "username");

        WipLimit wipLimit = new WipLimit(-1);

        Stage rootStage1 = new Stage(
                UUID.randomUUID().toString() + DateProvider.now(),
                workflow.getWorkflowId(),
                NullLane.newInstance(), "rootstage1",
                wipLimit, 0, LaneType.Standard);

//        SwimLane swimLane = new SwimLane(UUID.randomUUID().toString() + DateProvider.now(), workflow.getWorkflowId(), rootStage1, "swimlane1", wipLimit, 0, LaneType.Standard);
//        rootStage1.addLane(swimLane);
        Lane swimLane = rootStage1.createSwimLane("swimlane1", wipLimit.getValue(), LaneType.Standard);

        workflow.addStage(rootStage1);

        assertEquals(1, workflow.getStages().size());
        assertEquals(1, workflow.getStages().get(0).getChildren().size());
        workflowRepository.save(workflow);

        workflow = workflowRepository.findById(workflow.getId()).get();
        assertEquals(1, workflow.getStages().size());
        assertEquals(1, workflow.getStages().get(0).getChildren().size());

        workflow.deleteLaneById(swimLane.getId(), userId, username, boardId);
        workflowRepository.save(workflow);

        assertEquals(1, workflow.getStages().size());
        assertEquals(0, workflow.getStages().get(0).getChildren().size());
    }
}
