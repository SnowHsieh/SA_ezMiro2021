package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnterBoardUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void enter_board() {
        String boardId = create_board();
        eventListener.clear();
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId("userId");

        enterBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(boardId, output.getId());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void should_create_cursor_when_board_entered() {
        String boardId = create_board();
        eventListener.clear();
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        String userId = "userId";
        input.setUserId(userId);

        enterBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(boardId, output.getId());
        assertEquals(2, eventListener.getEventCount());
        Cursor cursor = cursorRepository.findByUserId(userId).get();
        assertEquals(boardId, cursor.getBoardId());
        assertEquals(userId, cursor.getUserId());
        assertEquals(0, cursor.getCoordinate().getX(), 0.01);
        assertEquals(0, cursor.getCoordinate().getY(), 0.01);
    }
}