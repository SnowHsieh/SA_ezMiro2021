package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryInMemoryImpl;
import ntut.csie.selab.domain.MockFactory;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.enterboard.EnterBoardInput;
import ntut.csie.selab.usecase.board.enterboard.EnterBoardOutput;
import ntut.csie.selab.usecase.board.enterboard.EnterBoardUseCase;
import org.junit.Assert;
import org.junit.Test;

public class EnterBoardUseCaseTest {
    @Test
    public void enter_board_should_succeed() {
        // Arrange
        String boardId = "boardId";
        String userId = "userId";
        Board board = MockFactory.createBoard(boardId);
        BoardRepository boardRepository = new BoardRepositoryInMemoryImpl();
        DomainEventBus domainEventBus = new DomainEventBus();
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCase(boardRepository, domainEventBus);
        EnterBoardInput input = new EnterBoardInput();
        EnterBoardOutput output = new EnterBoardOutput();
        input.setBoardId(boardId);
        input.setUserId(userId);
        boardRepository.save(board);
        // Act
        enterBoardUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getCursor().stream().filter(cursor -> cursor.getUserId().equals(userId)).findFirst().orElse(null));
        Assert.assertEquals(1, output.getCursorCountInBoard());

    }
}
