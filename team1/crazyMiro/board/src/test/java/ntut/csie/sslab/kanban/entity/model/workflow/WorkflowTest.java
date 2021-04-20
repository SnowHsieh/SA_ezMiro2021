package ntut.csie.sslab.kanban.entity.model.workflow;

import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardBuilder;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.entity.model.workflow.event.*;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class WorkflowTest {
    private String boardId = "boardId";
    private String userId = "userId";
    private String username = "username";
    private Workflow workflow;
    private String rootStageId;

    @BeforeEach
    public void setUp() {
        should_publish_workflow_created_domain_event_when_create_workflow();
    }

    private void should_publish_workflow_created_domain_event_when_create_workflow() {
        workflow = WorkflowBuilder.newInstance()
                .boardId(boardId)
                .name("First workflow")
                .userId(userId)
                .username(username)
                .build();

        assertNotNull(workflow);
        assertEquals(boardId, workflow.getBoardId());
        assertEquals("First workflow", workflow.getName());

        assertEquals(WorkflowCreated.class.getSimpleName(), workflow.getDomainEvents().get(0).getClass().getSimpleName());
        assertEquals(workflow.getWorkflowId(), ((WorkflowCreated)workflow.getDomainEvents().get(0)).workflowId());
        assertEquals(workflow.getName(), ((WorkflowCreated)workflow.getDomainEvents().get(0)).workflowName());
        assertEquals(userId, ((WorkflowCreated)workflow.getDomainEvents().get(0)).userId());
        assertEquals(username, ((WorkflowCreated)workflow.getDomainEvents().get(0)).username());
        assertEquals(workflow.getBoardId(), ((WorkflowCreated)workflow.getDomainEvents().get(0)).boardId());

        assertEquals(1, workflow.getDomainEvents().size());
    }

    @Test
    public void should_publish_workflow_deleted_domain_event_when_delete_workflow(){
        workflow = WorkflowBuilder.newInstance()
                .boardId(boardId)
                .name("First workflow")
                .userId(userId)
                .username(username)
                .build();
        workflow.markAsRemove(userId, username, boardId);

        assertEquals(2, workflow.getDomainEvents().size());
        Assertions.assertEquals(WorkflowDeleted.class.getSimpleName(), ((WorkflowDeleted)workflow.getDomainEvents().get(1)).getClass().getSimpleName());
        assertEquals(workflow.getWorkflowId(), ((WorkflowDeleted)workflow.getDomainEvents().get(1)).workflowId());
        assertEquals(workflow.getName(), ((WorkflowDeleted)workflow.getDomainEvents().get(1)).workflowName());
        assertEquals(userId, ((WorkflowDeleted)workflow.getDomainEvents().get(1)).userId());
        assertEquals(username, ((WorkflowDeleted)workflow.getDomainEvents().get(1)).username());
        assertEquals(boardId, ((WorkflowDeleted)workflow.getDomainEvents().get(1)).boardId());
    }

    @Test
    public void should_publish_stage_created_domain_event_when_create_stage(){
        assertEquals(0, workflow.getStages().size());
        String stageName = "backlog";
        rootStageId = workflow.createStage(stageName, -1,LaneType.Backlog, "-1", userId, username, boardId);
        assertEquals(1, workflow.getStages().size());
        assertEquals(workflow.getWorkflowId(), workflow.getStages().get(0).getWorkflowId());
        assertEquals(NullLane.ID, workflow.getStages().get(0).getParent().getId());
        assertEquals(stageName, workflow.getStages().get(0).getName());
        assertEquals(-1, workflow.getStages().get(0).getWipLimit().getValue());
        assertEquals(0, workflow.getStages().get(0).getOrder());
        assertEquals(LaneType.Backlog, workflow.getStages().get(0).getType());
        assertEquals(0, workflow.getStages().get(0).getChildren().size());
        assertEquals(0, workflow.getStages().get(0).getCommittedCards().size());
        assertEquals(LaneLayout.Vertical, workflow.getStages().get(0).getLayout());

        assertEquals(2, workflow.getDomainEvents().size());
        assertEquals(StageCreated.class.getSimpleName(), workflow.getDomainEvents().get(1).getClass().getSimpleName());
        assertEquals(rootStageId, ((StageCreated)workflow.getDomainEvents().get(1)).stageId());
        assertEquals(workflow.getWorkflowId(), ((StageCreated)workflow.getDomainEvents().get(1)).workflowId());
        assertEquals(NullLane.ID, ((StageCreated)workflow.getDomainEvents().get(1)).parentId());
        assertEquals(stageName, ((StageCreated)workflow.getDomainEvents().get(1)).name());
        assertEquals(LaneType.Backlog.toString(), ((StageCreated)workflow.getDomainEvents().get(1)).type());
        assertEquals(userId, ((StageCreated)workflow.getDomainEvents().get(1)).userId());
        assertEquals(username, ((StageCreated)workflow.getDomainEvents().get(1)).username());
        assertEquals(boardId, ((StageCreated)workflow.getDomainEvents().get(1)).boardId());
        workflow.clearDomainEvents();
    }

    @Test
    public void should_publish_stage_created_domain_event_when_create_stage_in_root_stage() {
        should_publish_stage_created_domain_event_when_create_stage();
        assertEquals(0, workflow.getDomainEvents().size());
        String stageName = "miniStage";
        Stage rootStage = (Stage) workflow.getLaneById(workflow.getStages().get(0).getId()).get();

        workflow.createStage(stageName,  2, LaneType.Standard, rootStage.getId(), userId, username, boardId);
        assertEquals(1, workflow.getDomainEvents().size());
        rootStage = (Stage)workflow.getLaneById(rootStage.getId()).get();
        assertEquals(1, rootStage.getChildren().size());

        Lane miniStage = rootStage.getChildren().get(0);
        assertEquals(workflow.getWorkflowId(), miniStage.getWorkflowId());
        assertEquals(rootStage.getId(), miniStage.getParent().getId());
        assertEquals(stageName, miniStage.getName());
        assertEquals(2, miniStage.getWipLimit().getValue());
        assertEquals(LaneType.Standard, miniStage.getType());

        assertEquals(StageCreated.class, workflow.getDomainEvents().get(0).getClass());
        assertEquals(miniStage.getId(), ((StageCreated)workflow.getDomainEvents().get(0)).stageId());
        assertEquals(workflow.getWorkflowId(), ((StageCreated)workflow.getDomainEvents().get(0)).workflowId());
        assertEquals(miniStage.getParent().getId(), ((StageCreated)workflow.getDomainEvents().get(0)).parentId());
        assertEquals(miniStage.getName(), ((StageCreated)workflow.getDomainEvents().get(0)).name());
        assertEquals(LaneType.Standard.toString(), ((StageCreated)workflow.getDomainEvents().get(0)).type());
        assertEquals(userId, ((StageCreated)workflow.getDomainEvents().get(0)).userId());
        assertEquals(username, ((StageCreated)workflow.getDomainEvents().get(0)).username());
        assertEquals(boardId, ((StageCreated)workflow.getDomainEvents().get(0)).boardId());
        workflow.clearDomainEvents();
    }

    @Test
    public void should_publish_swimlane_created_domain_event_when_create_swimLane_in_root_stage() {
        should_publish_stage_created_domain_event_when_create_stage();
        assertEquals(0, workflow.getDomainEvents().size());

        Stage rootStage= (Stage) workflow.getLaneById(workflow.getStages().get(0).getId()).get();
        workflow.createSwimLane(rootStage.getId(),"swimLane", 3, LaneType.Archive, userId, username, boardId);
        assertEquals(1, workflow.getDomainEvents().size());

        assertEquals(1, rootStage.getChildren().size());
        Lane swimLane= rootStage.getChildren().get(0);
        assertEquals(workflow.getWorkflowId(), swimLane.getWorkflowId());
        assertEquals(rootStage.getId(), swimLane.getParent().getId());
        assertEquals("swimLane", swimLane.getName());
        assertEquals(3, swimLane.getWipLimit().getValue());
        assertEquals(LaneType.Archive, swimLane.getType());

        SwimLaneCreated domainEvent = (SwimLaneCreated) workflow.getDomainEvents().get(0);
        assertEquals(SwimLaneCreated.class, domainEvent.getClass());
        assertEquals(workflow.getWorkflowId(), domainEvent.workflowId());
        assertEquals(swimLane.getParent().getId(), domainEvent.parentId());
        assertEquals(swimLane.getId(),  domainEvent.swimLaneId());
        assertEquals(swimLane.getName(), domainEvent.name());
        assertEquals(userId, domainEvent.userId());
        assertEquals(username, domainEvent.username());
        assertEquals(boardId, domainEvent.boardId());

        workflow.clearDomainEvents();
    }

    @Test
    public void should_publish_stage_deleted_domain_event_when_delete_stage() {
        should_publish_stage_created_domain_event_when_create_stage_in_root_stage();
        assertEquals(0, workflow.getDomainEvents().size());

        Lane rootStage = workflow.getLaneById(rootStageId).get();
        Lane subStage = rootStage.getChildren().get(0);
        String rootStageId = workflow.deleteLaneById(rootStage.getId(), userId, username, boardId);

        assertEquals(0, workflow.getStages().size());
        assertEquals(2, workflow.getDomainEvents().size());

        StageDeleted subStageDeleted = (StageDeleted) workflow.getDomainEvents().get(0);
        assertEquals(StageDeleted.class.getSimpleName(), subStageDeleted.getClass().getSimpleName());
        assertNotNull(subStageDeleted.stageId());
        assertEquals(subStage.getId(), subStageDeleted.stageId());
        assertEquals(subStage.getName(), subStageDeleted.name());
        assertEquals(workflow.getWorkflowId(), subStageDeleted.workflowId());
        assertEquals(rootStageId, subStageDeleted.parentId());
        assertEquals(userId, subStageDeleted.userId());
        assertEquals(username, subStageDeleted.username());
        assertEquals(boardId,  subStageDeleted.boardId());

        StageDeleted rootStageDeleted = (StageDeleted) workflow.getDomainEvents().get(1);
        assertEquals(StageDeleted.class, rootStageDeleted.getClass());
        assertEquals(rootStageId, rootStageDeleted.stageId());
        assertEquals(rootStage.getName(), rootStageDeleted.name());
        assertEquals(workflow.getWorkflowId(), rootStageDeleted.workflowId());
        assertEquals(NullLane.ID, rootStageDeleted.parentId());
        assertEquals(userId, rootStageDeleted.userId());
        assertEquals(username, rootStageDeleted.username());
        assertEquals(boardId, rootStageDeleted.boardId());
    }

    @Test
    public void should_publish_swimlane_deleted_domain_event_when_delete_swim_lane_in_root_stage() {
        should_publish_swimlane_created_domain_event_when_create_swimLane_in_root_stage();
        assertEquals(0, workflow.getDomainEvents().size());

        Lane deletedSwimLane= workflow.getStages().get(0).getChildren().get(0);

        workflow.deleteLaneById(deletedSwimLane.getId(), userId, username, boardId);
        Lane rootStage= workflow.getStages().get(0);
        assertEquals(0, rootStage.getChildren().size());
        assertEquals(1,workflow.getDomainEvents().size());

        SwimLaneDeleted domainEvent = (SwimLaneDeleted) workflow.getDomainEvents().get(0);
        assertEquals(SwimLaneDeleted.class, domainEvent.getClass());
        assertEquals(workflow.getWorkflowId(), domainEvent.workflowId());
        assertEquals(deletedSwimLane.getId(), domainEvent.swimLaneId());
        assertEquals(rootStage.getId(),domainEvent.parentId());
        assertEquals(deletedSwimLane.getName(),  domainEvent.name());
        assertEquals(userId, domainEvent.userId());
        assertEquals(username,  domainEvent.username());
        assertEquals(boardId, domainEvent.boardId());
    }

    @Test
    public void should_publish_swimlane_deleted_and_stage_deleted_domain_event_when_delete_root_stage_containing_swimlane() {
        should_publish_swimlane_created_domain_event_when_create_swimLane_in_root_stage();
        assertEquals(0, workflow.getDomainEvents().size());

        String deletedRootStageId = workflow.deleteLaneById(rootStageId, userId, username, boardId);
        assertEquals(0, workflow.getStages().size());
        assertEquals(2, workflow.getDomainEvents().size());

        SwimLaneDeleted swimlaneDeleted = (SwimLaneDeleted) workflow.getDomainEvents().get(0);
        assertEquals(SwimLaneDeleted.class, swimlaneDeleted.getClass());
        assertEquals(deletedRootStageId, swimlaneDeleted.parentId());
        assertNotNull(swimlaneDeleted.swimLaneId());
        assertEquals(workflow.getWorkflowId(), swimlaneDeleted.workflowId());
        assertEquals(userId, swimlaneDeleted.userId());
        assertEquals(username, swimlaneDeleted.username());
        assertEquals(boardId, swimlaneDeleted.boardId());

        StageDeleted domainEvent = (StageDeleted) workflow.getDomainEvents().get(1);
        assertEquals(StageDeleted.class, domainEvent.getClass());
        assertEquals(workflow.getWorkflowId(), domainEvent.workflowId());
        assertEquals("-1", domainEvent.parentId());
        assertEquals(deletedRootStageId, domainEvent.stageId());
        assertEquals(userId, domainEvent.userId());
        assertEquals(username, domainEvent.username());
        assertEquals(boardId, domainEvent.boardId());
    }



    @Test
    public void should_publish_card_committed_domain_event_when_commit_card() {
        should_publish_stage_created_domain_event_when_create_stage_in_root_stage();
        assertEquals(0, workflow.getDomainEvents().size());

        Lane rootStage = workflow.getStages().get(0);
        Card card= CardBuilder.newInstance()
                .workflowId(workflow.getWorkflowId())
                .userId("userId")
                .type(CardType.General)
                .laneId(rootStage.getId())
                .notes("notes")
                .estimate("xl")
                .deadline("2020/02/18")
                .description("card")
                .build();
        assertEquals(rootStage.getId(), card.getLaneId());
        assertEquals(CardType.General, card.getType());
        assertEquals("notes", card.getNotes());
        assertEquals("xl", card.getEstimate());
        assertEquals("2020/02/18", card.getDeadline());
        assertEquals("card", card.getDescription());
        assertNotNull(card.getCardId());

        assertEquals(0, rootStage.getCommittedCards().size());
        workflow.commitCardInLane(
                card.getCardId(),
                rootStage.getId(),
                0,
                userId,
                username,
                boardId);
        assertEquals(1, rootStage.getCommittedCards().size());
        assertEquals(1, workflow.getDomainEvents().size());
        assertEquals(1, card.getDomainEvents().size());

        CardCommitted domainEvent = (CardCommitted) workflow.getDomainEvents().get(0);
        assertEquals(CardCommitted.class, domainEvent.getClass());

        assertEquals(card.getCardId(), domainEvent.cardId());
        assertEquals(rootStage.getId(), domainEvent.newLaneId());
        assertEquals(workflow.getWorkflowId(), domainEvent.workflowId());
        assertEquals(0, domainEvent.order());
        assertEquals(userId, domainEvent.userId());
        assertEquals(username, domainEvent.username());
        assertEquals(boardId, domainEvent.boardId());

        workflow.clearDomainEvents();
    }

    @Test
    public void should_publish_card_uncommitted_domain_event_when_uncommit_card(){
        should_publish_card_committed_domain_event_when_commit_card();
        assertEquals(0, workflow.getDomainEvents().size());

        String cardId = workflow.getLaneById(rootStageId).get().getCommittedCards().get(0).getCardId();
        workflow.uncommitCardInLane(cardId, rootStageId, userId, username, boardId);
        assertEquals(0, workflow.getLaneById(rootStageId).get().getCommittedCards().size());
        assertEquals(1, workflow.getDomainEvents().size());

        CardUncommitted domainEvent = (CardUncommitted) workflow.getDomainEvents().get(0);

        assertEquals(CardUncommitted.class, domainEvent.getClass());
        assertEquals(cardId, domainEvent.cardId());
        assertEquals(rootStageId, domainEvent.laneId());
        assertEquals(workflow.getWorkflowId(), domainEvent.workflowId());
        assertEquals(userId, domainEvent.userId());
        assertEquals(username, domainEvent.username());
        assertEquals(boardId, domainEvent.boardId());

    }

    @Test
    public void should_publish_stage_moved_domain_event_when_move_stage() {
        assertEquals(1, workflow.getDomainEvents().size());

        String firstRootStageTitle = "first";
        String secondRootStageTitle = "second";
        String thirdRootStageTitle = "third";

        String firstRootStageId = createRootStage(firstRootStageTitle, -1, LaneType.Standard);
        String secondRootStageId = createRootStage(secondRootStageTitle, -1, LaneType.Standard);
        String thirdRootStageId = createRootStage(thirdRootStageTitle, -1, LaneType.Standard);

        assertEquals(4, workflow.getDomainEvents().size());
        assertEquals(0, workflow.getLaneById(firstRootStageId).get().getOrder());
        assertEquals(1, workflow.getLaneById(secondRootStageId).get().getOrder());
        assertEquals(2, workflow.getLaneById(thirdRootStageId).get().getOrder());

        workflow.moveStage(secondRootStageId, 2, userId, username, boardId);
        assertEquals(5, workflow.getDomainEvents().size());
        assertEquals(0, workflow.getLaneById(firstRootStageId).get().getOrder());
        assertEquals(1, workflow.getLaneById(thirdRootStageId).get().getOrder());
        assertEquals(2, workflow.getLaneById(secondRootStageId).get().getOrder());

        assertEquals(StageMoved.class.getSimpleName(), workflow.getDomainEvents().get(4).getClass().getSimpleName());
        assertEquals(workflow.getWorkflowId(), ((StageMoved)workflow.getDomainEvents().get(4)).workflowId());
        assertEquals(secondRootStageId, ((StageMoved)workflow.getDomainEvents().get(4)).stageId());
        assertEquals(2, ((StageMoved)workflow.getDomainEvents().get(4)).newOrder());
        assertEquals(secondRootStageTitle, ((StageMoved)workflow.getDomainEvents().get(4)).stageName());
        assertEquals(userId, ((StageMoved)workflow.getDomainEvents().get(4)).userId());
        assertEquals(username, ((StageMoved)workflow.getDomainEvents().get(4)).userName());
        assertEquals(boardId, ((StageMoved)workflow.getDomainEvents().get(4)).boardId());
        assertEquals(1, ((StageMoved)workflow.getDomainEvents().get(4)).oldOrder());


        workflow.moveStage(firstRootStageId, 1, userId, username, boardId);
        assertEquals(6, workflow.getDomainEvents().size());
        assertEquals(2, workflow.getLaneById(secondRootStageId).get().getOrder());
        assertEquals(0, workflow.getLaneById(thirdRootStageId).get().getOrder());
        assertEquals(1, workflow.getLaneById(firstRootStageId).get().getOrder());

        assertEquals(StageMoved.class.getSimpleName(), workflow.getDomainEvents().get(5).getClass().getSimpleName());
        assertEquals(workflow.getWorkflowId(), ((StageMoved)workflow.getDomainEvents().get(5)).workflowId());
        assertEquals(firstRootStageId, ((StageMoved)workflow.getDomainEvents().get(5)).stageId());
        assertEquals(1, ((StageMoved)workflow.getDomainEvents().get(5)).newOrder());
        assertEquals(firstRootStageTitle, ((StageMoved)workflow.getDomainEvents().get(5)).stageName());
        assertEquals(userId, ((StageMoved)workflow.getDomainEvents().get(5)).userId());
        assertEquals(username, ((StageMoved)workflow.getDomainEvents().get(5)).userName());
        assertEquals(boardId, ((StageMoved)workflow.getDomainEvents().get(5)).boardId());
        assertEquals(0, ((StageMoved)workflow.getDomainEvents().get(5)).oldOrder());

    }

    @Test
    public void should_publish_lane_renamed_domain_event_when_rename_swimlane() {
        String stageId = createRootStage("rootStageName", -1, LaneType.Standard);

        String oldName = "swimLaneName";
        String newName = "newName";
        String swimLaneId = workflow.createSwimLane(stageId, oldName, -1, LaneType.Standard, userId, username, boardId);
        assertEquals(oldName, workflow.getLaneById(swimLaneId).get().getName());

        workflow.renameLane(swimLaneId, newName, userId, username);
        SwimLane swimLane = (SwimLane) workflow.getLaneById(swimLaneId).get();
        assertEquals(newName, swimLane.getName());
        assertEquals(4, workflow.getDomainEvents().size());
        assertEquals(LaneRenamed.class, workflow.getDomainEvents().get(3).getClass());
        assertEquals(swimLane.getName(), ((LaneRenamed)workflow.getDomainEvents().get(3)).newName());
        assertEquals(oldName, ((LaneRenamed)workflow.getDomainEvents().get(3)).oldName());
        assertEquals(swimLaneId, ((LaneRenamed)workflow.getDomainEvents().get(3)).laneId());
        assertEquals(userId, ((LaneRenamed)workflow.getDomainEvents().get(3)).userId());
        assertEquals(username, ((LaneRenamed)workflow.getDomainEvents().get(3)).username());
        assertEquals(boardId, ((LaneRenamed)workflow.getDomainEvents().get(3)).boardId());
        assertEquals(LaneLayout.Horizontal.toString(), ((LaneRenamed)workflow.getDomainEvents().get(3)).layout());
    }

    @Test
    public void should_get_empty_lane_when_get_lane_by_non_existing_id() {
        createRootStage("backlog", -1, LaneType.Backlog);

        Optional<Lane> lane = workflow.getLaneById("123");
        assertFalse(lane.isPresent());
    }

    @Test
    public void should_publish_wipLimit_set_domain_event_when_set_wipLimit(){
        String stageId = createRootStage("rootStageTitle", 3, LaneType.Standard);
        workflow.setLaneWipLimit(stageId, 5, userId, username);
        assertEquals(3, workflow.getDomainEvents().size());
        Lane stage = workflow.getLaneById(stageId).get();
        assertEquals(5, stage.getWipLimit().getValue());
        assertEquals(WipLimitSet.class.getSimpleName(), workflow.getDomainEvents().get(2).getClass().getSimpleName());
        assertEquals(workflow.getWorkflowId(), ((WipLimitSet)workflow.getDomainEvents().get(2)).workflowId());
        assertEquals(3, ((WipLimitSet)workflow.getDomainEvents().get(2)).oldWipLimit());
        assertEquals(5, ((WipLimitSet)workflow.getDomainEvents().get(2)).newWipLimit());
        assertEquals(stage.getId(), ((WipLimitSet)workflow.getDomainEvents().get(2)).laneId());
        assertEquals(userId, ((WipLimitSet)workflow.getDomainEvents().get(2)).userId());
        assertEquals(username, ((WipLimitSet)workflow.getDomainEvents().get(2)).username());
        assertEquals(boardId, ((WipLimitSet)workflow.getDomainEvents().get(2)).boardId());
        assertEquals(LaneLayout.Vertical.toString(), ((WipLimitSet)workflow.getDomainEvents().get(2)).layout());
    }

    private String createRootStage(String title, int WIPLimit, LaneType laneType){
        return workflow.createStage(title, WIPLimit, laneType, "-1", userId, username, boardId);
    }

}
