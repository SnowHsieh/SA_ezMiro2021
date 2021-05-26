package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.Cursor;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorInput;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCase;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoveCursorUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_move_cursor() {
        String boardId = createBoard(projectId, boardName);
        String boardSessionId = enterBoard(boardId, userId);

        Board board = boardRepository.findById(boardId).get();
        assertNotNull(board.getUserCursor(userId));

        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCaseImpl(boardRepository, domainEventBus);
        MoveCursorInput moveCursorInput = moveCursorUseCase.newInput();
        CqrsCommandPresenter moveCursorOutput = CqrsCommandPresenter.newInstance();

        moveCursorInput.setBoardId(boardId);
        moveCursorInput.setUserId(userId);
        moveCursorInput.setPositionX(10);
        moveCursorInput.setPositionY(10);

        moveCursorUseCase.execute(moveCursorInput, moveCursorOutput);

        assertNotNull(moveCursorOutput.getId());
        assertEquals(ExitCode.SUCCESS, moveCursorOutput.getExitCode());

        board = boardRepository.findById(boardId).get();
        Cursor cursor = board.getUserCursor(userId);
        assertEquals(userId, cursor.userId());
        assertEquals(10, cursor.positionX());
        assertEquals(10, cursor.positionY());
    }
}
