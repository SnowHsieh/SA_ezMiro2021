package ntut.csie.islab.miro.usecase.board;


import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardInput;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;



public class CreateBoardUseCaseTest  extends AbstractSpringBootJpaTest {
//setUp in AbstractSpringBootJpaTest

    @Test
    public void test_create_board(){
        CreateBoardUseCase createBoardUseCase= new CreateBoardUseCase(domainEventBus,boardRepository);
        CreateBoardInput input =  createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId(UUID.randomUUID());
        input.setBoardName("Board name will");
        createBoardUseCase.execute(input,output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        UUID boardId = UUID.fromString(output.getId());
        Board board = this.boardRepository.findById(boardId).get();
        assertNotNull(board);
    }
}
