package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.adapter.board.BoardRepositoryImpl;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.usecase.board.create.CreateBoardInput;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateBoardUseCaseTest {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() throws Exception {
        boardRepository = new BoardRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void should_succeed_when_create_board() {

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardOutput = CqrsCommandPresenter.newInstance();

        createBoardInput.setProjectId("1");
        createBoardInput.setBoardName("Board Name");

        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        assertNotNull(createBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, createBoardOutput.getExitCode());

        Board board = boardRepository.findById(createBoardOutput.getId()).get();
        assertEquals(createBoardOutput.getId(), board.getBoardId());
        assertEquals("1", board.getProjectId());
        assertEquals("Board Name", board.getName());
    }
}
