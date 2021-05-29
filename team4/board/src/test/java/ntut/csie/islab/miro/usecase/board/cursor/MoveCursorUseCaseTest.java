package ntut.csie.islab.miro.usecase.board.cursor;

import ntut.csie.islab.miro.entity.model.board.BoardSession;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.cursor.Cursor;
import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardInput;
import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoveCursorUseCaseTest extends AbstractSpringBootJpaTest {


    @BeforeEach
    @Override
    public void setUp(){
        super.setUp();
        create_a_test_cursor();
    }

    private void create_a_test_cursor() {
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCase(domainEventBus,boardRepository);
        EnterBoardInput enterBoardInput = enterBoardUseCase.newInput();
        CqrsCommandPresenter enterBoardOutput = CqrsCommandPresenter.newInstance();
        enterBoardInput.setBoardId(board.getBoardId());
        enterBoardInput.setUserId(userId);
        enterBoardUseCase.execute(enterBoardInput, enterBoardOutput);
    }

    @Test
    public void move_a_cursor() {

        Position newPosition = new Position(1.0,1.0);
        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCase(boardRepository, domainEventBus);
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setUserId(userId);
        input.setBoardId(boardId);
        input.setPosition(newPosition);

        moveCursorUseCase.execute(input, output);

        Board resolveBoard= boardRepository.findById(boardId).get();
        BoardSession boardSession = resolveBoard.getBoardSessionList().stream().filter(x->x.getUserId().equals(userId)).findFirst().get();
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        // TODO: check other user can receive domain event
    }

}
