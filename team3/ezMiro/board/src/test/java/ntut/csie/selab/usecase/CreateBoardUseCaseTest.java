package ntut.csie.selab.usecase;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.CreateBoardUseCase;
import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;
import org.junit.Assert;
import org.junit.Test;

public class CreateBoardUseCaseTest {
    @Test
    public void create_board_should_succeed() throws Exception{
        // Arrange
        DomainEventBus domainEventBus = new DomainEventBus();
        BoardRepository boardRepository = new BoardRepositoryImpl();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, domainEventBus);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();
        input.setTeamId("1");
        input.setBoardName("firstBoard");

        // Act
        createBoardUseCase.execute(input, output);

        // Assert
        Assert.assertEquals("1", output.getTeamId());
        Assert.assertEquals("firstBoard", output.getBoardName());
        Assert.assertTrue(boardRepository.findById(output.getBoardId()).isPresent());
        Assert.assertEquals("1", boardRepository.findById(output.getBoardId()).get().getTeamId());
        Assert.assertEquals(1, domainEventBus.getCount());
    }
}
