package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.Coordinate;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorUseCase;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCursorUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void create_a_cursor() {
        String boardId = UUID.randomUUID().toString();
        String ip = "123.123.0.1";
        String sessionId = "1";
        CreateCursorUseCase createCursorUseCase = new CreateCursorUseCaseImpl(cursorRepository, domainEventBus);
        CreateCursorInput input = createCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setIp(ip);
        input.setSessionId(sessionId);

        createCursorUseCase.execute(input, output);

        assertTrue(cursorRepository.findById(output.getId()).isPresent());
        Cursor cursor = cursorRepository.findById(output.getId()).get();
        assertEquals(ip, cursor.getIp());
        assertEquals(boardId, cursor.getBoardId());
        assertEquals(sessionId, cursor.getSessionId());
        assertTrue(new Coordinate(0,0).equals(cursor.getPosition()));
        assertEquals(1, eventListener.getEventCount());
    }
}
