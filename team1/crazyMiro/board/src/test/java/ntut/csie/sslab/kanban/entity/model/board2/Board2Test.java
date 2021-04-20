package ntut.csie.sslab.kanban.entity.model.board2;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class Board2Test {

    private Board2 createBoard(){
        return new Board2("teamId", "boardId", "Scrum Board", "userId");
    }

    @Test
    public void create_a_valid_board() {
        String userId = "userId";
        String teamId = "teamId";
        String boardId = "boardId";
        String boardName = "First board";

        Board2 board2 = new Board2(teamId, boardId, boardName, userId);

        assertEquals(teamId, board2.getTeamId());
        assertEquals(boardId, board2.getBoardId());
        assertEquals(boardName, board2.getName());
        Assertions.assertEquals(0, board2.getCommittedWorkflows().size());
        Assertions.assertEquals(0, board2.getBoardMembers().size());
    }

    @Test
    public void commit_a_workflow() {
        Board2 board2 = createBoard();

        board2.commitWorkflow("workflowId");

        assertEquals(1, board2.getCommittedWorkflows().size());
    }

    @Test
    public void uncommit_an_existing_workflow() {
        Board2 board2 = createBoard();
        board2.commitWorkflow("workflowId");

        board2.uncommitworkflow("workflowId");

        assertEquals(0, board2.getCommittedWorkflows().size());
    }

    @Test
    public void add_a_board_member_increases_the_number_of_board_member_by_one() {
        Board2 board2 = createBoard();
        String userId = "newUserId";

        board2.becameBoardMember(BoardMemberType.Member, userId);

        assertEquals(1, board2.getBoardMembers().size());
        BoardMember boardMember = board2.getBoardMembers().get(0);
        assertEquals(userId, boardMember.getUserId());
        assertEquals(board2.getBoardId(), boardMember.getBoardId());
        assertEquals(BoardMemberType.Member, boardMember.getMemberType());
    }

    @Test
    public void remove_an_valid_board_member_decreases_the_number_of_board_member_by_one() {
        Board2 board2 = createBoard();
        String userId = "newUserId";
        board2.becameBoardMember(BoardMemberType.Member, userId);

        board2.removeBoardMember(userId);

        Assertions.assertEquals(0, board2.getBoardMembers().size());
    }


    @Test
    public void change_a_board_name_to_a_new_name() {
        Board2 board2 = createBoard();
        String oldName = board2.getName();
        String newName = "newBoardName";

        board2.renameName(newName);

        assertEquals(newName, board2.getName());
    }

    @Test
    public void a_board_member_enters_a_board_is_added_to_the_board_session(){
        Board2 board2 = createBoard();
        String userId = "newUserId";
        board2.becameBoardMember(BoardMemberType.Member, userId);

        board2.acceptUserEntry(userId);

        assertEquals(1, board2.getBoardSessions().size());
        assertEquals(userId, board2.getBoardSessions().get(0).getUserId());
        assertEquals(board2.getBoardId(), board2.getBoardSessions().get(0).getBoardId());
    }

    @Test
    public void leave_a_board_with_a_valid_session_id_removes_it_from_the_board_session(){
        Board2 board2 = createBoard();
        String userId = "newUserId";
        board2.becameBoardMember(BoardMemberType.Member, userId);
        String boardSessionId = board2.acceptUserEntry(userId);

        board2.acceptUserLeaving(boardSessionId);

        assertEquals(0, board2.getBoardSessions().size());
    }
}
