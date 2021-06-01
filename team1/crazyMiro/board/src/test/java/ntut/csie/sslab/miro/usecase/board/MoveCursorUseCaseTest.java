package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.board.moveCursor.MoveCursorInput;
import ntut.csie.sslab.miro.usecase.board.moveCursor.MoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.board.moveCursor.MoveCursorUseCaseImpl;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveCursorUseCaseTest extends AbstractSpringBootJpaTest {
    @Test
    public void move_a_cursor(){
        String boardId = UUID.randomUUID().toString();
        createBoard(boardId, "Jay board");
        String userId = "user1";
        String boardSessionId = enterBoard(boardId, userId);
        eventListener.clearEventCount();
        Coordinate position = new Coordinate(1,2);
        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCaseImpl(boardRepository, domainEventBus);
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setPosition(position);

        moveCursorUseCase.execute(input, output);

        assertEquals(1,eventListener.getEventCount());


    }
}
