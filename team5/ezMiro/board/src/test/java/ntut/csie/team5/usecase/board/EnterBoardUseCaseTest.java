package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.enter.EnterBoardInput;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnterBoardUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_enter_board() {
        String boardId = createBoard(projectId, boardName);

        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput enterBoardInput = enterBoardUseCase.newInput();
        CqrsCommandPresenter enterBoardOutput = CqrsCommandPresenter.newInstance();

        enterBoardInput.setBoardId(boardId);
        enterBoardInput.setUserId(userId);

        enterBoardUseCase.execute(enterBoardInput, enterBoardOutput);

        assertNotNull(enterBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, enterBoardOutput.getExitCode());

        Board board = boardRepository.findById(boardId).orElse(null);
        assertNotNull(board);
        assertEquals(1, board.getBoardSessions().size());
        assertEquals(enterBoardOutput.getId(), board.getBoardSessions().get(0).boardSessionId());
        assertEquals(boardId, board.getBoardSessions().get(0).boardId());
        assertEquals(userId, board.getBoardSessions().get(0).userId());
    }
}
