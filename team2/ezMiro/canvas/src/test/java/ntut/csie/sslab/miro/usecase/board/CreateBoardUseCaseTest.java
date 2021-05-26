package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateBoardUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void create_a_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId("TeamId");
        input.setBoardName("Team2sBoard");

        createBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertNotNull(boardRepository.findById(output.getId()).get());
        assertEquals("TeamId", boardRepository.findById(output.getId()).get().getTeamId());
        assertEquals("Team2sBoard", boardRepository.findById(output.getId()).get().getBoardName());
        assertNotNull(boardRepository.findById(output.getId()).get().getBoardChannel());
        assertEquals(1, eventListener.getEventCount());
    }
}