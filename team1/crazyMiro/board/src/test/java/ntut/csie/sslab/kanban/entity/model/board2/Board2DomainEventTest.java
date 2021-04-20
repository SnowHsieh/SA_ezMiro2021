package ntut.csie.sslab.kanban.entity.model.board2;

import ntut.csie.sslab.kanban.entity.model.board2.event.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class Board2DomainEventTest {
    private String userId = "userId";

    private Board2 createBoard(){
        return new Board2("teamId", "boardId", "Scrum Board", userId);
    }

    @Test
    public void create_a_board_publishes_a_board_created_domain_event() {
        Board2 board2 = createBoard();

        assertEquals(1, board2.getDomainEvents().size());
        BoardCreated domainEvent = (BoardCreated) board2.getDomainEvents().get(0);

        assertEquals(board2.getTeamId(), domainEvent.teamId());
        assertEquals(board2.getBoardId(), domainEvent.boardId());
        assertEquals(board2.getName(), domainEvent.name());
        assertEquals(userId, domainEvent.userId());
    }

    @Test
    public void commit_a_workflow_publishes_a_workflow_committed_domain_event() {
        Board2 board2 = createBoard();
        board2.clearDomainEvents();

        board2.commitWorkflow("workflowId");

        assertEquals(1, board2.getDomainEvents().size());
        WorkflowCommitted workflowCommitted = (WorkflowCommitted) board2.getDomainEvents().get(0);
        assertEquals(board2.getBoardId(), workflowCommitted.boardId());
        assertEquals(board2.getCommittedWorkflows().get(0).getWorkflowId(), workflowCommitted.workflowId());
    }

    @Test
    public void uncommit_a_workflow_publishes_a_workflow_uncommitted_domain_event() {
        Board2 board2 = createBoard();
        String workflowId = "workflowId";
        board2.commitWorkflow(workflowId);
        board2.clearDomainEvents();

        board2.uncommitworkflow(workflowId);

        assertEquals(1, board2.getDomainEvents().size());
        WorkflowUncommitted workflowUncommitted = (WorkflowUncommitted) board2.getDomainEvents().get(0);
        assertEquals(board2.getBoardId(), workflowUncommitted.boardId());
        assertEquals(workflowId, workflowUncommitted.workflowId());
    }

    @Test
    public void add_a_board_member_publishes_a_board_member_added_domain_event() {
        Board2 board2 = createBoard();
        board2.clearDomainEvents();

        board2.becameBoardMember(BoardMemberType.Member, userId);

        assertEquals(1, board2.getDomainEvents().size());
        BoardMemberAdded boardMemberAdded = (BoardMemberAdded) board2.getDomainEvents().get(0);
        assertEquals(board2.getBoardId(), boardMemberAdded.boardId());
        assertEquals(board2.getBoardMembers().get(0).getUserId(), boardMemberAdded.userId());
        assertEquals(board2.getBoardMembers().get(0).getMemberType().name(), boardMemberAdded.memberType());
    }

    @Test
    public void remove_a_board_member_publishes_a_board_member_removed_domain_event() {
        Board2 board2 = createBoard();
        board2.becameBoardMember(BoardMemberType.Member, userId);
        board2.clearDomainEvents();

        board2.removeBoardMember(userId);

        assertEquals(1, board2.getDomainEvents().size());
        BoardMemberRemoved boardMemberRemoved = (BoardMemberRemoved) board2.getDomainEvents().get(0);
        assertEquals(board2.getBoardId(), boardMemberRemoved.boardId());
        assertEquals(userId, boardMemberRemoved.userId());
    }


    @Test
    public void rename_a_board_publishes_a_board_renamed_domain_event() {
        Board2 board2 = createBoard();
        board2.clearDomainEvents();
        String oldName = board2.getName();
        String newName = "newBoardName";

        board2.renameName(newName);

        assertEquals(1, board2.getDomainEvents().size());
        BoardRenamed boardRenamed = (BoardRenamed) board2.getDomainEvents().get(0);
        assertEquals(board2.getBoardId(), boardRenamed.boardId());
        assertEquals(oldName, boardRenamed.oldName());
        assertEquals(newName, boardRenamed.newName());
    }

    @Test
    public void enter_a_board_publishes_a_board_entered_domain_event(){
        Board2 board2 = createBoard();
        board2.becameBoardMember(BoardMemberType.Member, userId);
        board2.clearDomainEvents();

        board2.acceptUserEntry(userId);

        assertEquals(1, board2.getDomainEvents().size());
        BoardEntered boardEntered = (BoardEntered) board2.getDomainEvents().get(0);
        assertEquals(userId, boardEntered.userId());
        assertEquals(board2.getBoardId(), boardEntered.boardId());
        assertEquals(board2.getBoardSessions().get(0).getBoardSessionId(), boardEntered.boardSessionId());
    }

    @Test
    public void leave_a_board_publishes_a_board_left_domain_event(){
        Board2 board2 = createBoard();
        board2.becameBoardMember(BoardMemberType.Member, userId);
        String boardSessionId = board2.acceptUserEntry(userId);
        board2.clearDomainEvents();

        board2.acceptUserLeaving(boardSessionId);

        assertEquals(1, board2.getDomainEvents().size());
        BoardLeft boardLeft = (BoardLeft) board2.getDomainEvents().get(0);
        assertEquals(userId, boardLeft.userId());
        assertEquals(board2.getBoardId(), boardLeft.boardId());
        assertEquals(boardSessionId, boardLeft.boardSessionId());
    }
}
