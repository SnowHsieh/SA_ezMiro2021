package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorUseCase;
import ntut.csie.sslab.kanban.usecase.cursor.create.CreateCursorUseCaseImpl;
import ntut.csie.sslab.kanban.usecase.cursor.move.MoveCursorInput;
import ntut.csie.sslab.kanban.usecase.cursor.move.MoveCursorUseCase;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveCursorUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void move_a_cursor() {
        String boardId = UUID.randomUUID().toString();
        String ip = "123.123.0.1";
        String cursorId = createCursor(boardId, ip);
        eventListener.clearEventCount();
        Coordinate newPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCaseImpl(cursorRepository, domainEventBus);
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setCursorId(cursorId);
        input.setPosition(newPosition);

        moveCursorUseCase.execute(input, output);

        Cursor cursor = cursorRepository.findById(cursorId).get();
        assertTrue(cursor.getPosition().equals(newPosition));
        assertEquals(1,eventListener.getEventCount());
    }

}
