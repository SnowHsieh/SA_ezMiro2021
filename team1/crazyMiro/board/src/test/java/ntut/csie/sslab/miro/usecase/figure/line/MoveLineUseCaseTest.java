package ntut.csie.sslab.miro.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.line.move.MoveLineInput;
import ntut.csie.sslab.miro.usecase.figure.line.move.MoveLineUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.move.MoveLineUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MoveLineUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void move_a_line_by_target_position() {
        String boardId = UUID.randomUUID().toString();
        Coordinate newTargetPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate sourcePosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        String lineId = createLine(boardId,
                "-1",
                "-1",
                sourcePosition,
                new Coordinate(new Random().nextLong(), new Random().nextLong()));
        eventListener.clearEventCount();
        MoveLineUseCase moveLineUseCase = new MoveLineUseCaseImpl(lineRepository, domainEventBus);
        MoveLineInput input = moveLineUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);
        input.setSourcePosition(sourcePosition);
        input.setTargetPosition(newTargetPosition);

        moveLineUseCase.execute(input, output);

        assertTrue(lineRepository.findById(lineId).isPresent());
        Line line = lineRepository.findById(lineId).get();
        assertTrue(line.getSourcePosition().equals(sourcePosition));
        assertTrue(line.getTargetPosition().equals(newTargetPosition));
        assertEquals(1, eventListener.getEventCount());
    }

    @Test
    public void move_a_line_by_source_position() {
        String boardId = UUID.randomUUID().toString();
        Coordinate newSourcePosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        Coordinate targetPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        String lineId = createLine(boardId,
                "-1",
                "-1",
                new Coordinate(new Random().nextLong(), new Random().nextLong()),
                targetPosition);
        eventListener.clearEventCount();
        MoveLineUseCase moveLineUseCase = new MoveLineUseCaseImpl(lineRepository, domainEventBus);
        MoveLineInput input = moveLineUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);
        input.setSourcePosition(newSourcePosition);
        input.setTargetPosition(targetPosition);

        moveLineUseCase.execute(input, output);

        assertTrue(lineRepository.findById(lineId).isPresent());
        Line line = lineRepository.findById(lineId).get();
        assertTrue(line.getSourcePosition().equals(newSourcePosition));
        assertTrue(line.getTargetPosition().equals(targetPosition));
        assertEquals(1, eventListener.getEventCount());
    }
}
