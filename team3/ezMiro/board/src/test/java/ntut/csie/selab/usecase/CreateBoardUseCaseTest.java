package ntut.csie.selab.usecase;

import ntut.csie.selab.usecase.board.CreateBoardUseCase;
import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;
import org.junit.Assert;
import org.junit.Test;

public class CreateBoardUseCaseTest {
    @Test
    public void create_board() throws Exception{

        // Arrange
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase();
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();
        input.setTeamId("1");
        input.setBoardName("firstBoard");

        // Act
        createBoardUseCase.execute(input, output);

        // Assert
        Assert.assertEquals("1", output.getTeamId());
        Assert.assertEquals("firstBoard", output.getBoardName());
    }
}
