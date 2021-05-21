package ntut.csie.islab.miro.usecase.board.cursor;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardInput;
import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnterBoardUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    private Board board;
    private UUID userId;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
//        boardRepository = new BoardRepositoryImpl(new BoardRepositoryListPeer());
        board = new Board(UUID.randomUUID(),"boardName");
        boardRepository.save(board);
        userId = UUID.randomUUID();
    }

    @Test
    public void should_succeed_when_enter_board() {

        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCase(domainEventBus,boardRepository);
        EnterBoardInput enterBoardInput = enterBoardUseCase.newInput();
        CqrsCommandPresenter enterBoardOutput = CqrsCommandPresenter.newInstance();

        enterBoardInput.setBoardId(board.getBoardId());
        enterBoardInput.setUserId(userId);

        enterBoardUseCase.execute(enterBoardInput, enterBoardOutput);

        assertNotNull(enterBoardOutput.getId());
        Assert.assertEquals(ExitCode.SUCCESS, enterBoardOutput.getExitCode());

        Board resolveBoard = boardRepository.findById(board.getBoardId()).get();
        Assert.assertEquals(1, resolveBoard.getBoardSessionList().size());
        Assert.assertEquals(board.getBoardId(), resolveBoard.getBoardSessionList().get(0).getBoardId());
        Assert.assertEquals(userId, resolveBoard.getBoardSessionList().get(0).getUserId());
        Assert.assertEquals(1, resolveBoard.getCursorList().size());
    }

}
