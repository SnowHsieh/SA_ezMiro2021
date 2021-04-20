package ntut.csie.sslab.kanban.usecase.board2;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.entity.model.board2.BoardMember;
import ntut.csie.sslab.kanban.entity.model.board2.BoardMemberType;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.board2.create.CreateBoard2Input;
import ntut.csie.sslab.kanban.usecase.board2.create.CreateBoard2UseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CreateBoard22UseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
    }


    @Test
    public void should_succeed_when_create_board_in_team() {

        CreateBoard2UseCase createBoard2UseCase = newCreateBoard2UseCase();

        CreateBoard2Input input = createBoard2UseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setTeamId(teamId);
        input.setName(boardName);
        input.setUserId(userId);
        createBoard2UseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Board2 board2 = board2Repository.findById(output.getId()).get();
        assertEquals(output.getId(), board2.getBoardId());
        assertEquals(boardName, board2.getName());
        assertEquals(teamId, board2.getTeamId());

        BoardMember boardMember = board2.getBoardMemberById(userId).get();
        assertEquals(userId, boardMember.getUserId());
        assertEquals(board2.getBoardId(), boardMember.getBoardId());
        Assertions.assertEquals(BoardMemberType.Manager, boardMember.getMemberType());
    }
}
