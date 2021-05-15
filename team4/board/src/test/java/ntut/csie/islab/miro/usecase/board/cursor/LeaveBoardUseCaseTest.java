package ntut.csie.islab.miro.usecase.board.cursor;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LeaveBoardUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    private Board board;
    private UUID userId;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
        board = new Board(UUID.randomUUID(),"boardName");
        boardRepository.save(board);
        userId = UUID.randomUUID();
    }

    @Test
    public void should_succeed_when_leave_board() {

        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCase(domainEventBus,boardRepository);
        EnterBoardInput enterBoardInput = enterBoardUseCase.newInput();
        CqrsCommandPresenter enterBoardOutput = CqrsCommandPresenter.newInstance();

        enterBoardInput.setBoardId(board.getBoardId());
        enterBoardInput.setUserId(userId);

        enterBoardUseCase.execute(enterBoardInput, enterBoardOutput);

        assertNotNull(enterBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, enterBoardOutput.getExitCode());


        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCase(boardRepository, domainEventBus);
        LeaveBoardInput leaveBoardInput = leaveBoardUseCase.newInput();
        CqrsCommandPresenter leaveBoardOutput = CqrsCommandPresenter.newInstance();

        leaveBoardInput.setBoardId(board.getBoardId());
        leaveBoardInput.setUserId(userId);
        leaveBoardInput.setBoardSessionId(enterBoardOutput.getId());

        leaveBoardUseCase.execute(leaveBoardInput, leaveBoardOutput);

        assertNotNull(leaveBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, leaveBoardOutput.getExitCode());

        Board resolveBoard = boardRepository.findById(board.getBoardId()).get();
        assertEquals(0, resolveBoard.getBoardSessionList().size());
        assertEquals(0, resolveBoard.getCursorList().size());
    }

}
