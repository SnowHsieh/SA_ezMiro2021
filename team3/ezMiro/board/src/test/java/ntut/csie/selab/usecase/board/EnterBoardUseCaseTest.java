package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.domain.MockFactory;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.model.DomainEventBus;
import org.junit.Assert;
import org.junit.Test;

public class EnterBoardUseCaseTest {
    @Test
    public void enter_board_should_succeed() {
        // Arrange
        String bordId = "bordId";
        String userId = "userId";
        Board board = MockFactory.createBoard(bordId);
        BoardRepository boardRepository = new BoardRepositoryImpl();
        DomainEventBus domainEventBus = new DomainEventBus();
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCase(boardRepository, domainEventBus);
        EnterBoardInput input = new EnterBoardInput();
        EnterBoardOutput output = new EnterBoardOutput();
        input.setBoardId(bordId);
        input.setUserId(userId);
        boardRepository.add(board);
        // Act
        enterBoardUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getCursor().stream().filter(cursor -> cursor.getUserId().equals(userId)).findFirst().orElse(null));
        Assert.assertEquals(1, output.getCursorCountInBoard());

    }
}
