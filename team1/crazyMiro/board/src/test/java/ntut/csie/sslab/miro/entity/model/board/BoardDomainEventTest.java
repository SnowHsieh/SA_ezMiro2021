//package ntut.csie.sslab.kanban.entity.model.board;
//
//import ntut.csie.sslab.kanban.entity.model.board.event.*;
//import org.junit.jupiter.api.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//public class BoardDomainEventTest {
//    private String userId = "userId";
//
//    private Board createBoard(){
//        return new Board("teamId", "boardId", "Scrum Board", userId);
//    }
//
//    @Test
//    public void create_a_board_publishes_a_board_created_domain_event() {
//        Board board = createBoard();
//
//        assertEquals(1, board.getDomainEvents().size());
//        BoardCreated domainEvent = (BoardCreated) board.getDomainEvents().get(0);
//
//        assertEquals(board.getTeamId(), domainEvent.teamId());
//        assertEquals(board.getBoardId(), domainEvent.boardId());
//        assertEquals(board.getName(), domainEvent.name());
//        assertEquals(userId, domainEvent.userId());
//    }
//
//    @Test
//    public void commit_a_workflow_publishes_a_workflow_committed_domain_event() {
//        Board board = createBoard();
//        board.clearDomainEvents();
//
//        board.commitWorkflow("workflowId");
//
//        assertEquals(1, board.getDomainEvents().size());
//        WorkflowCommitted workflowCommitted = (WorkflowCommitted) board.getDomainEvents().get(0);
//        assertEquals(board.getBoardId(), workflowCommitted.boardId());
//        assertEquals(board.getCommittedWorkflows().get(0).getWorkflowId(), workflowCommitted.workflowId());
//    }
//
//    @Test
//    public void uncommit_a_workflow_publishes_a_workflow_uncommitted_domain_event() {
//        Board board = createBoard();
//        String workflowId = "workflowId";
//        board.commitWorkflow(workflowId);
//        board.clearDomainEvents();
//
//        board.uncommitworkflow(workflowId);
//
//        assertEquals(1, board.getDomainEvents().size());
//        WorkflowUncommitted workflowUncommitted = (WorkflowUncommitted) board.getDomainEvents().get(0);
//        assertEquals(board.getBoardId(), workflowUncommitted.boardId());
//        assertEquals(workflowId, workflowUncommitted.workflowId());
//    }
//
//    @Test
//    public void add_a_board_member_publishes_a_board_member_added_domain_event() {
//        Board board = createBoard();
//        board.clearDomainEvents();
//
//        board.becameBoardMember(BoardMemberType.Member, userId);
//
//        assertEquals(1, board.getDomainEvents().size());
//        BoardMemberAdded boardMemberAdded = (BoardMemberAdded) board.getDomainEvents().get(0);
//        assertEquals(board.getBoardId(), boardMemberAdded.boardId());
//        assertEquals(board.getBoardMembers().get(0).getUserId(), boardMemberAdded.userId());
//        assertEquals(board.getBoardMembers().get(0).getMemberType().name(), boardMemberAdded.memberType());
//    }
//
//    @Test
//    public void remove_a_board_member_publishes_a_board_member_removed_domain_event() {
//        Board board = createBoard();
//        board.becameBoardMember(BoardMemberType.Member, userId);
//        board.clearDomainEvents();
//
//        board.removeBoardMember(userId);
//
//        assertEquals(1, board.getDomainEvents().size());
//        BoardMemberRemoved boardMemberRemoved = (BoardMemberRemoved) board.getDomainEvents().get(0);
//        assertEquals(board.getBoardId(), boardMemberRemoved.boardId());
//        assertEquals(userId, boardMemberRemoved.userId());
//    }
//
//
//    @Test
//    public void rename_a_board_publishes_a_board_renamed_domain_event() {
//        Board board = createBoard();
//        board.clearDomainEvents();
//        String oldName = board.getName();
//        String newName = "newBoardName";
//
//        board.renameName(newName);
//
//        assertEquals(1, board.getDomainEvents().size());
//        BoardRenamed boardRenamed = (BoardRenamed) board.getDomainEvents().get(0);
//        assertEquals(board.getBoardId(), boardRenamed.boardId());
//        assertEquals(oldName, boardRenamed.oldName());
//        assertEquals(newName, boardRenamed.newName());
//    }
//
//    @Test
//    public void enter_a_board_publishes_a_board_entered_domain_event(){
//        Board board = createBoard();
//        board.becameBoardMember(BoardMemberType.Member, userId);
//        board.clearDomainEvents();
//
//        board.acceptUserEntry(userId);
//
//        assertEquals(1, board.getDomainEvents().size());
//        BoardEntered boardEntered = (BoardEntered) board.getDomainEvents().get(0);
//        assertEquals(userId, boardEntered.userId());
//        assertEquals(board.getBoardId(), boardEntered.boardId());
//        assertEquals(board.getBoardSessions().get(0).getBoardSessionId(), boardEntered.boardSessionId());
//    }
//
//    @Test
//    public void leave_a_board_publishes_a_board_left_domain_event(){
//        Board board = createBoard();
//        board.becameBoardMember(BoardMemberType.Member, userId);
//        String boardSessionId = board.acceptUserEntry(userId);
//        board.clearDomainEvents();
//
//        board.acceptUserLeaving(boardSessionId);
//
//        assertEquals(1, board.getDomainEvents().size());
//        BoardLeft boardLeft = (BoardLeft) board.getDomainEvents().get(0);
//        assertEquals(userId, boardLeft.userId());
//        assertEquals(board.getBoardId(), boardLeft.boardId());
//        assertEquals(boardSessionId, boardLeft.boardSessionId());
//    }
//}
