package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.miro.entity.model.board.event.*;
import ntut.csie.sslab.miro.entity.model.workflow.Workflow;
import ntut.csie.sslab.miro.entity.model.workflow.WorkflowBuilder;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BoardTest {
    private String teamId = "teamId";
    private String userId = "userId";
    private Board board;

    @BeforeEach
    public void setUp() {
        should_publish_board_created_domain_event_when_create_board();
    }

    private void should_publish_board_created_domain_event_when_create_board() {
        String boardName = "First board";

        board = BoardBuilder.newInstance()
                .teamId(teamId)
                .name(boardName)
                .userId(userId)
                .build();

        Assertions.assertEquals(teamId, board.getTeamId());
        Assertions.assertEquals(boardName, board.getName());
        
        Assertions.assertEquals(1, board.getDomainEvents().size());
        Assertions.assertEquals(BoardCreated.class.getSimpleName(),board.getDomainEvents().get(0).getClass().getSimpleName());
        Assertions.assertEquals(teamId, ((BoardCreated)board.getDomainEvents().get(0)).teamId());
        Assertions.assertEquals(board.getBoardId(), ((BoardCreated)board.getDomainEvents().get(0)).boardId());
        Assertions.assertEquals(board.getName(), ((BoardCreated)board.getDomainEvents().get(0)).name());
        Assertions.assertEquals(userId, ((BoardCreated)board.getDomainEvents().get(0)).userId());
        board.clearDomainEvents();
    }

    private String should_publish_workflow_committed_domain_event_when_commit_workflow() {
        Assertions.assertEquals(0, board.getCommittedWorkflows().size());
        String workflowTitle = "first workflow";

        Workflow workflow = WorkflowBuilder.newInstance()
                .name(workflowTitle)
                .boardId(board.getBoardId())
                .build();

        assertNotNull(workflow.getWorkflowId());
        Assertions.assertEquals(workflowTitle, workflow.getName());
        Assertions.assertEquals(board.getBoardId(), workflow.getBoardId());

        board.commitWorkflow(workflow.getWorkflowId());
        Assertions.assertEquals(1, board.getCommittedWorkflows().size());

        Assertions.assertEquals(1, board.getDomainEvents().size());
        Assertions.assertEquals(WorkflowCommitted.class.getSimpleName(), board.getDomainEvents().get(0).getClass().getSimpleName());
        Assertions.assertEquals(board.getBoardId(), ((WorkflowCommitted)board.getDomainEvents().get(0)).boardId());
        Assertions.assertEquals(workflow.getWorkflowId(), ((WorkflowCommitted)board.getDomainEvents().get(0)).workflowId());
        return workflow.getWorkflowId();
    }

    @Test
    public void should_publish_workflow_uncommitted_domain_event_when_uncommit_workflow() {
        String workflowId = should_publish_workflow_committed_domain_event_when_commit_workflow();

        board.uncommitworkflow(workflowId);
        Assertions.assertEquals(0, board.getCommittedWorkflows().size());

        Assertions.assertEquals(2, board.getDomainEvents().size());
        Assertions.assertEquals(WorkflowUncommitted.class.getSimpleName(), board.getDomainEvents().get(1).getClass().getSimpleName());
        Assertions.assertEquals(board.getBoardId(), ((WorkflowUncommitted)board.getDomainEvents().get(1)).boardId());
        Assertions.assertEquals(workflowId, ((WorkflowUncommitted)board.getDomainEvents().get(1)).workflowId());
        board.clearDomainEvents();
    }

    @Test
    public void should_publish_board_member_added_domain_event_when_add_board_member() {
        Assertions.assertEquals(0, board.getBoardMembers().size());

        String userId = "newUserId";

        board.becameBoardMember(BoardMemberType.Member, userId);
        Assertions.assertEquals(1, board.getBoardMembers().size());

        BoardMember boardMember = board.getBoardMembers().get(0);
        Assertions.assertEquals(userId, boardMember.getUserId());
        Assertions.assertEquals(board.getBoardId(), boardMember.getBoardId());
        Assertions.assertEquals(BoardMemberType.Member, boardMember.getMemberType());

        Assertions.assertEquals(1, board.getDomainEvents().size());
        Assertions.assertEquals(BoardMemberAdded.class.getSimpleName(), board.getDomainEvents().get(0).getClass().getSimpleName());
        Assertions.assertEquals(board.getBoardId(), ((BoardMemberAdded)board.getDomainEvents().get(0)).boardId());
        Assertions.assertEquals(board.getBoardMembers().get(0).getUserId(), ((BoardMemberAdded)board.getDomainEvents().get(0)).userId());
        Assertions.assertEquals(board.getBoardMembers().get(0).getMemberType().toString(), ((BoardMemberAdded)board.getDomainEvents().get(0)).memberType());
        board.clearDomainEvents();
    }

    @Test
    public void should_publish_board_member_removed_domain_event_when_remove_board_member() {
        should_publish_board_member_added_domain_event_when_add_board_member();

        String removedUserId = "";
        for(BoardMember boardMember : board.getBoardMembers()){
            if(boardMember.getMemberType() == BoardMemberType.Member)
                removedUserId = boardMember.getUserId();
        }
        board.removeBoardMember(removedUserId);

        Assertions.assertEquals(0, board.getBoardMembers().size());
        Assertions.assertEquals(1, board.getDomainEvents().size());
        Assertions.assertEquals(BoardMemberRemoved.class.getSimpleName(), board.getDomainEvents().get(0).getClass().getSimpleName());
        Assertions.assertEquals(board.getBoardId(), ((BoardMemberRemoved)board.getDomainEvents().get(0)).boardId());
        Assertions.assertEquals(removedUserId, ((BoardMemberRemoved)board.getDomainEvents().get(0)).userId());
        board.clearDomainEvents();
    }


    @Test
    public void should_publish_board_title_changed_domain_event_when_change_board_title() {
        String oldName = board.getName();
        String newName = "newBoardName";
        board.renameName(newName);
        Assertions.assertEquals(newName, board.getName());
        Assertions.assertEquals(1, board.getDomainEvents().size());
        Assertions.assertEquals(BoardRenamed.class, board.getDomainEvents().get(0).getClass());
        Assertions.assertEquals(board.getBoardId(), ((BoardRenamed)board.getDomainEvents().get(0)).boardId());
        Assertions.assertEquals(oldName, ((BoardRenamed)board.getDomainEvents().get(0)).oldName());
        Assertions.assertEquals(newName, ((BoardRenamed)board.getDomainEvents().get(0)).newName());
        board.clearDomainEvents();
    }
    @Test
    public void should_publish_board_entered_domain_event_when_enter_board(){
        board.acceptUserEntry(userId);
        Assertions.assertEquals(1, board.getBoardSessions().size());
        Assertions.assertEquals(userId, board.getBoardSessions().get(0).getUserId());
        Assertions.assertEquals(board.getBoardId(), board.getBoardSessions().get(0).getBoardId());

        Assertions.assertEquals(1, board.getDomainEvents().size());
        Assertions.assertEquals(BoardEntered.class.getSimpleName(), ((BoardEntered)board.getDomainEvents().get(0)).getClass().getSimpleName());
        Assertions.assertEquals(userId, ((BoardEntered)board.getDomainEvents().get(0)).userId());
        Assertions.assertEquals(board.getBoardId(), ((BoardEntered)board.getDomainEvents().get(0)).boardId());
        Assertions.assertEquals(board.getBoardSessions().get(0).getBoardSessionId(), ((BoardEntered)board.getDomainEvents().get(0)).boardSessionId());
        board.clearDomainEvents();
    }

    @Test
    public void should_publish_board_left_domain_event_when_leave_board(){
        String boardSessionId = board.acceptUserEntry(userId);
        Assertions.assertEquals(1, board.getBoardSessions().size());
        board.acceptUserLeaving(boardSessionId);
        Assertions.assertEquals(2, board.getDomainEvents().size());
        Assertions.assertEquals(BoardLeft.class.getSimpleName(), ((BoardLeft)board.getDomainEvents().get(1)).getClass().getSimpleName());
        Assertions.assertEquals(userId, ((BoardLeft)board.getDomainEvents().get(1)).userId());
        Assertions.assertEquals(board.getBoardId(), ((BoardLeft)board.getDomainEvents().get(1)).boardId());
        Assertions.assertEquals(boardSessionId, ((BoardLeft)board.getDomainEvents().get(1)).boardSessionId());
        board.clearDomainEvents();
    }
}
