package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.domain.MockFactory;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorInput;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorOutput;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Objects;

public class MoveCursorUseCaseTest {
    @Test
    public void move_cursor_should_succeed() {
        // Arrange
        MoveCursorInput input = new MoveCursorInput();
        MoveCursorOutput output = new MoveCursorOutput();
        BoardRepository boardRepository = new BoardRepositoryImpl();
        DomainEventBus domainEventBus = new DomainEventBus();
        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCase(boardRepository, domainEventBus);
        String boardId = "boardId";
        String userId = "userId";
        Point point = new Point(100, 100);
        Board board = MockFactory.createBoard(boardId);
        boardRepository.add(board);
        board.addCursor(new Cursor(boardId, userId, point));
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setPoint(point);

        // Act
        moveCursorUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(boardId, output.getBoardId());
        Assert.assertEquals(point, Objects.requireNonNull(output.getCursors().stream().filter(cursor -> cursor.getUserId().equals(userId)).findFirst().orElse(null)).getPoint());
    }
}
