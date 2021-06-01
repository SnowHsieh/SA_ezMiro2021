package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateCursorUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void create_cursor() {
        String boardId = create_board();
        eventListener.clear();
        CreateCursorUseCase createCursorUseCase = new CreateCursorUseCaseImpl(cursorRepository, domainEventBus);
        CreateCursorInput input = createCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId("userId");
        input.setCoordinate(new Coordinate(3, 4));

        createCursorUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(boardId, cursorRepository.findById(output.getId()).get().getBoardId());
        assertEquals("userId", cursorRepository.findById(output.getId()).get().getUserId());
        assertEquals(3, cursorRepository.findById(output.getId()).get().getCoordinate().getX());
        assertEquals(4, cursorRepository.findById(output.getId()).get().getCoordinate().getY());
        assertEquals(1, eventListener.getEventCount());
    }
}