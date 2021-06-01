package ntut.csie.team5.usecase.board;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.event.CursorMoved;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorInput;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCase;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoveCursorUseCaseTest extends AbstractTest {

    private CursorMovedCatchers cursorMovedCatchers;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        cursorMovedCatchers = new CursorMovedCatchers(domainEventBus);
        domainEventBus.register(cursorMovedCatchers);
    }

    @Test
    public void should_succeed_when_move_cursor() {
        String boardId = createBoard(projectId, boardName);
        String boardSessionId = enterBoard(boardId, userId);

        Board board = boardRepository.findById(boardId).orElse(null);
        assertNotNull(board);

        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCaseImpl(boardRepository, domainEventBus);
        MoveCursorInput moveCursorInput = moveCursorUseCase.newInput();
        CqrsCommandPresenter moveCursorOutput = CqrsCommandPresenter.newInstance();

        moveCursorInput.setBoardId(boardId);
        moveCursorInput.setBoardSessionId(boardSessionId);
        moveCursorInput.setPositionX(10);
        moveCursorInput.setPositionY(10);

        moveCursorUseCase.execute(moveCursorInput, moveCursorOutput);

        assertNotNull(moveCursorOutput.getId());
        assertEquals(ExitCode.SUCCESS, moveCursorOutput.getExitCode());

        List<CursorMoved> cursorMoveds = cursorMovedCatchers.getCursorMoveds();
        assertEquals(1, cursorMoveds.size());
        assertEquals(boardId, cursorMoveds.get(0).boardId());
        assertEquals(boardSessionId, cursorMoveds.get(0).boardSessionId());
        assertEquals(10, cursorMoveds.get(0).positionX());
        assertEquals(10, cursorMoveds.get(0).positionY());
    }

    private static class CursorMovedCatchers {

        private final DomainEventBus domainEventBus;
        private final List<CursorMoved> cursorMoveds;

        public CursorMovedCatchers(DomainEventBus domainEventBus) {
            this.domainEventBus = domainEventBus;
            this.cursorMoveds = new ArrayList<>();
        }

        @Subscribe
        public void whenCursorMoved(CursorMoved cursorMoved) {
            this.cursorMoveds.add(cursorMoved);
        }

        public List<CursorMoved> getCursorMoveds() {
            return cursorMoveds;
        }
    }
}
