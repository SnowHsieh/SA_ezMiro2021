//package ntut.csie.sslab.kanban.entity.model.board;
//
//import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//public class BoardTest {
//
//    private Board createBoard(){
//        return new Board("teamId", "boardId", "Scrum Board", "userId");
//    }
//
//    @Test
//    public void create_a_valid_board() {
//        String userId = "userId";
//        String teamId = "teamId";
//        String boardId = "boardId";
//        String boardName = "First board";
//
//        Board board = new Board(teamId, boardId, boardName, userId);
//
//        assertEquals(teamId, board.getTeamId());
//        assertEquals(boardId, board.getBoardId());
//        assertEquals(boardName, board.getName());
//        Assertions.assertEquals(0, board.getCommittedWorkflows().size());
//        Assertions.assertEquals(0, board.getBoardMembers().size());
//    }
//
//    @Test
//    public void commit_a_workflow() {
//        Board board = createBoard();
//
//        board.commitWorkflow("workflowId");
//
//        assertEquals(1, board.getCommittedWorkflows().size());
//    }
//
//    @Test
//    public void uncommit_an_existing_workflow() {
//        Board board = createBoard();
//        board.commitWorkflow("workflowId");
//
//        board.uncommitworkflow("workflowId");
//
//        assertEquals(0, board.getCommittedWorkflows().size());
//    }
//
//    @Test
//    public void add_a_board_member_increases_the_number_of_board_member_by_one() {
//        Board board = createBoard();
//        String userId = "newUserId";
//
//        board.becameBoardMember(BoardMemberType.Member, userId);
//
//        assertEquals(1, board.getBoardMembers().size());
//        BoardMember boardMember = board.getBoardMembers().get(0);
//        assertEquals(userId, boardMember.getUserId());
//        assertEquals(board.getBoardId(), boardMember.getBoardId());
//        assertEquals(BoardMemberType.Member, boardMember.getMemberType());
//    }
//
//    @Test
//    public void remove_an_valid_board_member_decreases_the_number_of_board_member_by_one() {
//        Board board = createBoard();
//        String userId = "newUserId";
//        board.becameBoardMember(BoardMemberType.Member, userId);
//
//        board.removeBoardMember(userId);
//
//        Assertions.assertEquals(0, board.getBoardMembers().size());
//    }
//
//
//    @Test
//    public void change_a_board_name_to_a_new_name() {
//        Board board = createBoard();
//        String oldName = board.getName();
//        String newName = "newBoardName";
//
//        board.renameName(newName);
//
//        assertEquals(newName, board.getName());
//    }
//
//    @Test
//    public void a_board_member_enters_a_board_is_added_to_the_board_session(){
//        Board board = createBoard();
//        String userId = "newUserId";
//        board.becameBoardMember(BoardMemberType.Member, userId);
//
//        board.acceptUserEntry(userId);
//
//        assertEquals(1, board.getBoardSessions().size());
//        assertEquals(userId, board.getBoardSessions().get(0).getUserId());
//        assertEquals(board.getBoardId(), board.getBoardSessions().get(0).getBoardId());
//    }
//
//    @Test
//    public void leave_a_board_with_a_valid_session_id_removes_it_from_the_board_session(){
//        Board board = createBoard();
//        String userId = "newUserId";
//        board.becameBoardMember(BoardMemberType.Member, userId);
//        String boardSessionId = board.acceptUserEntry(userId);
//
//        board.acceptUserLeaving(boardSessionId);
//
//        assertEquals(0, board.getBoardSessions().size());
//    }
//}
