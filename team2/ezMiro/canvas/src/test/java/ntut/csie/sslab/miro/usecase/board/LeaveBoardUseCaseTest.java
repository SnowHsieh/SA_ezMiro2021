package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardInput;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.leave.LeaveBoardUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class LeaveBoardUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void leave_board() {
        String userId = "userId";
        String boardId = create_board();
        enter_board(boardId, userId);
        eventListener.clear();
        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCaseImpl(boardRepository, domainEventBus);
        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);

        leaveBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(boardId, output.getId());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void should_remove_cursor_when_board_left() {
        String userId = "userId";
        String boardId = create_board();
        enter_board(boardId, userId);
        eventListener.clear();
        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCaseImpl(boardRepository, domainEventBus);
        LeaveBoardInput input = leaveBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);

        leaveBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(boardId, output.getId());
        assertEquals(2, eventListener.getEventCount());
        assertFalse(cursorRepository.findByUserId(userId).isPresent());
    }

    public void enter_board(String boardId, String userId) {
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);

        enterBoardUseCase.execute(input, output);
    }
}