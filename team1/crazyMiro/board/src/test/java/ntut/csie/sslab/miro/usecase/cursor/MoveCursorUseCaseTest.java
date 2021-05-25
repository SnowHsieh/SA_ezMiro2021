package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.move.MoveCursorUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveCursorUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void move_a_cursor() {
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        String userId = UUID.randomUUID().toString();
        enterBoard(boardId, userId);
        eventListener.clearEventCount();
        Coordinate newPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCaseImpl(cursorRepository, domainEventBus);
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setUserId(userId);
        input.setPosition(newPosition);

        moveCursorUseCase.execute(input, output);

        Cursor cursor = cursorRepository.findById(output.getId()).get();
        assertTrue(cursor.getPosition().equals(newPosition));
        assertEquals(1,eventListener.getEventCount());
    }

}
