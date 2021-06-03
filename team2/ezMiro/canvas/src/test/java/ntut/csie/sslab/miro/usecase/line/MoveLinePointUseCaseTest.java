package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.line.move.MoveLinePointInput;
import ntut.csie.sslab.miro.usecase.line.move.MoveLinePointUseCase;
import ntut.csie.sslab.miro.usecase.line.move.MoveLinePointUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoveLinePointUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void move_line_point() {
        String boardId = create_board();
        String lineId = create_line_without_connected_figures(boardId);
        eventListener.clear();
        MoveLinePointUseCase moveLinePointUseCase = new MoveLinePointUseCaseImpl(figureRepository, domainEventBus);
        MoveLinePointInput input = moveLinePointUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        Coordinate deltaCoordinate = new Coordinate(0,10);
        input.setLineId(lineId);
        input.setPointDelta(deltaCoordinate);
        input.setLinePoint(LinePoint.START);

        moveLinePointUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(20, line.getStartOffset().getX(), 0.001);
        assertEquals(40, line.getStartOffset().getY(), 0.001);
        assertEquals(40, line.getEndOffset().getX(), 0.001);
        assertEquals(50, line.getEndOffset().getY(), 0.001);
        assertEquals(1, eventListener.getEventCount());
    }
}