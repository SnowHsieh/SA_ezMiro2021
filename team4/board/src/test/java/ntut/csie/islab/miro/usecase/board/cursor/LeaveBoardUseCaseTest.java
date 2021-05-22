package ntut.csie.islab.miro.usecase.board.cursor;

import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardInput;
import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardUseCase;
import ntut.csie.islab.miro.usecase.board.leaveboard.LeaveBoardInput;
import ntut.csie.islab.miro.usecase.board.leaveboard.LeaveBoardUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LeaveBoardUseCaseTest extends AbstractSpringBootJpaTest {

    private CqrsCommandPresenter preGenerateEnterBoardPresenter;

    @BeforeEach
    @Override
    public void setUp(){
        super.setUp();
        preGenerateEnterBoardPresenter = generateEnterBoardUseCaseOutput();
    }

    private CqrsCommandPresenter generateEnterBoardUseCaseOutput(){
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCase(domainEventBus,boardRepository);
        EnterBoardInput enterBoardInput = enterBoardUseCase.newInput();
        CqrsCommandPresenter enterBoardOutput = CqrsCommandPresenter.newInstance();
        enterBoardInput.setBoardId(board.getBoardId());
        enterBoardInput.setUserId(userId);
        enterBoardUseCase.execute(enterBoardInput, enterBoardOutput);
        return  enterBoardOutput;
    }

    @Test
    public void should_succeed_when_leave_board() {


        LeaveBoardUseCase leaveBoardUseCase = new LeaveBoardUseCase(boardRepository, domainEventBus);
        LeaveBoardInput leaveBoardInput = leaveBoardUseCase.newInput();
        CqrsCommandPresenter leaveBoardOutput = CqrsCommandPresenter.newInstance();

        leaveBoardInput.setBoardId(board.getBoardId());
        leaveBoardInput.setUserId(userId);
        leaveBoardInput.setBoardSessionId(preGenerateEnterBoardPresenter.getId());

        leaveBoardUseCase.execute(leaveBoardInput, leaveBoardOutput);

        assertNotNull(leaveBoardOutput.getId());
        assertEquals(ExitCode.SUCCESS, leaveBoardOutput.getExitCode());

        Board resolveBoard = boardRepository.findById(board.getBoardId()).get();
        assertEquals(0, resolveBoard.getBoardSessionList().size());
//        assertEquals(0, resolveBoard.getCursorList().size());
    }

}
