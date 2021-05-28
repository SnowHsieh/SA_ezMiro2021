package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.create.CreateBoardUseCase;
import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class CreateBoardUseCaseTest {

    @Autowired
    BoardRepositoryPeer boardRepositoryPeer;

    @Test
    public void create_board_should_succeed() throws Exception{
        // Arrange
        DomainEventBus domainEventBus = new DomainEventBus();
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
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
