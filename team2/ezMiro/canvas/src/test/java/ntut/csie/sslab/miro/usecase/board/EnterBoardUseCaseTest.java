package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.usecase.DomainEventListener;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardInput;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.enter.EnterBoardUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnterBoardUseCaseTest {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;
    private DomainEventListener eventListener;

    @Before
    public void setUp() {
        boardRepository = new BoardRepositoryImpl();
        domainEventBus = new DomainEventBus();
        eventListener = new DomainEventListener();

        domainEventBus.register(eventListener);
    }

    @Test
    public void enter_board() {
        String boardId = create_board();
        eventListener.clear();
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput input = enterBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId("userId");

        enterBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(boardId, output.getId());
        assertEquals(1, eventListener.getEventCount());
    }

    private String create_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId("TeamId");
        input.setBoardName("Team2sBoard");

        createBoardUseCase.execute(input, output);

        return output.getId();
    }
}