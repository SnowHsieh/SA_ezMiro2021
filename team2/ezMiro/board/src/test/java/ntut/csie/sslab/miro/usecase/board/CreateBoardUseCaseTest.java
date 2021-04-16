package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardMember;
import ntut.csie.sslab.miro.entity.model.board.BoardMemberType;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CreateBoardUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
    }


    @Test
    public void should_succeed_when_create_board_in_team() {

        CreateBoardUseCase createBoardUseCase = newCreateBoardUseCase();

        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setTeamId(teamId);
        input.setName(boardName);
        input.setUserId(userId);
        createBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());

        Board board = boardRepository.findById(output.getId()).get();
        assertEquals(output.getId(), board.getBoardId());
        assertEquals(boardName, board.getName());
        assertEquals(teamId, board.getTeamId());

        BoardMember boardMember = board.getBoardMemberById(userId).get();
        assertEquals(userId, boardMember.getUserId());
        assertEquals(board.getBoardId(), boardMember.getBoardId());
        Assertions.assertEquals(BoardMemberType.Manager, boardMember.getMemberType());
    }
}
