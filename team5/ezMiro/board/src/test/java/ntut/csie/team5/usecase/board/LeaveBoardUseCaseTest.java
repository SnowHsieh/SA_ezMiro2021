package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.Cursor;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.enter.EnterBoardInput;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCaseImpl;
import ntut.csie.team5.usecase.board.leave.LeaveBoardInput;
import ntut.csie.team5.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.team5.usecase.board.leave.LeaveBoardUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeaveBoardUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_leave_board() {
        String boardId = createBoard(projectId, boardName);
        String boardSessionId = enterBoard(boardId, userId);

        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCaseImpl(boardRepository, domainEventBus);
        LeaveBoardInput leaveBoardInput = leaveBoardUseCase.newInput();
        CqrsCommandPresenter leaveBoardOutput = CqrsCommandPresenter.newInstance();

        leaveBoardInput.setBoardId(boardId);
        leaveBoardInput.setUserId(userId);
        leaveBoardInput.setBoardSessionId(boardSessionId);

        leaveBoardUseCase.execute(leaveBoardInput, leaveBoardOutput);

        assertNotNull(leaveBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, leaveBoardOutput.getExitCode());

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getBoardSessions().size());
        assertEquals(0, board.getCursors().size());
        assertNull(board.getUserCursor(userId));
    }
}
