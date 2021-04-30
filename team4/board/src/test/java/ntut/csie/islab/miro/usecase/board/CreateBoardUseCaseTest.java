package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;

public class CreateBoardUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
    }
    @Test
    public void test_create_board(){
        CreateBoardUseCase createBoardUseCase= new CreateBoardUseCase(domainEventBus,boardRepository);
        CreateBoardInput input =  createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId(UUID.randomUUID());
        input.setBoardName("Board name");

        createBoardUseCase.execute(input,output);
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
    }
}
