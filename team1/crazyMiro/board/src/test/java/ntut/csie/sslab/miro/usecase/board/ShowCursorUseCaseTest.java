package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.board.showCursor.ShowCursorInput;
import ntut.csie.sslab.miro.usecase.board.showCursor.ShowCursorUseCase;
import ntut.csie.sslab.miro.usecase.board.showCursor.ShowCursorUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowCursorUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void show_a_cursor() {
        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "Jay board");
        eventListener.clearEventCount();
        String userId = "user1";
        Coordinate position = new Coordinate(1,2);
        String boardSessionId = enterBoard(boardId, userId);
        eventListener.clearEventCount();
        ShowCursorUseCase showCursorUseCase = new ShowCursorUseCaseImpl(boardRepository, domainEventBus);
        ShowCursorInput input = showCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setPosition(position);

        showCursorUseCase.execute(input, output);

        assertEquals(1,eventListener.getEventCount());
    }
}
