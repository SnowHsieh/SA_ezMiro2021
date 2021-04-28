package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.usecase.DomainEventListener;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateBoardUseCaseTest {
    public BoardRepository boardRepository;
    public DomainEventBus domainEventBus;
    public DomainEventListener eventListener;

    @Before
    public void setUp() {
        boardRepository = new BoardRepositoryImpl();
        domainEventBus = new DomainEventBus();
        eventListener = new DomainEventListener();

        domainEventBus.register(eventListener);
    }

    @Test
    public void create_board() {
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
    }
}




