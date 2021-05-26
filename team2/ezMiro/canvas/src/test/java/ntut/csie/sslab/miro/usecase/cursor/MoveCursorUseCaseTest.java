package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoveCursorUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void move_cursor() {
        String boardId = create_board();
        String cursorId = create_cursor(boardId);
        eventListener.clear();
        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCaseImpl(cursorRepository, domainEventBus);
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setCursorId(cursorId);
        input.setCoordinate(new Coordinate(5, 22));

        moveCursorUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(boardId, cursorRepository.findById(output.getId()).get().getBoardId());
        assertEquals("userId", cursorRepository.findById(output.getId()).get().getUserId());
        assertEquals(5, cursorRepository.findById(output.getId()).get().getCoordinate().getX());
        assertEquals(22, cursorRepository.findById(output.getId()).get().getCoordinate().getY());
        assertEquals(1, eventListener.getEventCount());
    }
}