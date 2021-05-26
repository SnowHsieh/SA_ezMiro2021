package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.cursor.remove.RemoveCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.remove.RemoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.remove.RemoveCursorUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RemoveCursorUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void remove_cursor() {
        String boardId = create_board();
        String cursorId = create_cursor(boardId);
        eventListener.clear();
        RemoveCursorUseCase removeCursorUseCase = new RemoveCursorUseCaseImpl(cursorRepository, domainEventBus);
        RemoveCursorInput input = removeCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setCursorId(cursorId);

        removeCursorUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertFalse(cursorRepository.findById(cursorId).isPresent());
        assertEquals(1, eventListener.getEventCount());
    }
}