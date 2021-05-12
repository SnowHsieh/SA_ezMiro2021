package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.cursor.delete.DeleteCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.delete.DeleteCursorUseCase;
import ntut.csie.sslab.kanban.usecase.cursor.delete.DeleteCursorUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCursorUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void delete_a_cursor() {
        String boardId = UUID.randomUUID().toString();
        String ip = "123.123.0.1";
        String sessionId = "1";
        String cursorId = createCursor(boardId, ip, sessionId);
        eventListener.clearEventCount();
        DeleteCursorUseCase deleteCursorUseCase = new DeleteCursorUseCaseImpl(cursorRepository, domainEventBus);
        DeleteCursorInput input = deleteCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setCursorId(cursorId);

        deleteCursorUseCase.execute(input, output);

        assertTrue(cursorRepository.findById(cursorId).isPresent());
        assertEquals(1,eventListener.getEventCount());
    }

}