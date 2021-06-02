package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.line.move.MoveLineInput;
import ntut.csie.sslab.miro.usecase.line.move.MoveLineUseCase;
import ntut.csie.sslab.miro.usecase.line.move.MoveLineUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoveLineUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void move_line_without_connected_figures() {
        String boardId = create_board();
        String lineId = create_line_without_connected_figures(boardId);
        eventListener.clear();
        MoveLineUseCase moveLineUseCase = new MoveLineUseCaseImpl(figureRepository, domainEventBus);
        MoveLineInput input = moveLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        Coordinate deltaCoordinate = new Coordinate(0,10);
        input.setLineId(lineId);
        input.setDelta(deltaCoordinate);

        moveLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(20, line.getStartOffset().getX(), 0.001);
        assertEquals(40, line.getStartOffset().getY(), 0.001);
        assertEquals(40, line.getEndOffset().getX(), 0.001);
        assertEquals(60, line.getEndOffset().getY(), 0.001);
        assertEquals(1, eventListener.getEventCount());
    }
}