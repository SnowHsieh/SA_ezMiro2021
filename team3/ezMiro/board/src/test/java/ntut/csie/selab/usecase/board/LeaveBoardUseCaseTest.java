package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryInMemoryImpl;
import ntut.csie.selab.domain.MockFactory;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardInput;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardOutput;
import ntut.csie.selab.usecase.board.leaveboard.LeaveBoardUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class LeaveBoardUseCaseTest {
    @Test
    public void leave_board_should_succeed() {
        // Arrange
        String boardId = "boardId";
        String userId = "userId";
        Board board = MockFactory.createBoard(boardId);
        BoardRepository boardRepository = new BoardRepositoryInMemoryImpl();
        DomainEventBus domainEventBus = new DomainEventBus();
        board.addCursor(new Cursor(boardId, userId, new Point(100, 100)));
        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCase(boardRepository, domainEventBus);
        LeaveBoardInput input = new LeaveBoardInput();
        LeaveBoardOutput output = new LeaveBoardOutput();
        input.setBoardId(boardId);
        input.setUserId(userId);
        boardRepository.save(board);
        // Act
        leaveBoardUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(0, output.getCursorCountInBoard());

    }
}
