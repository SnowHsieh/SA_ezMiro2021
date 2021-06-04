package ntut.csie.sslab.miro.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionInput;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeTargetPositionUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void change_a_line_target_position() {
        String boardId = UUID.randomUUID().toString();
        Coordinate newTargetPosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        String lineId = createLine(boardId,
                "-1",
                "-1",
                new Coordinate(new Random().nextLong(), new Random().nextLong()),
                new Coordinate(new Random().nextLong(), new Random().nextLong()));
        eventListener.clearEventCount();
        ChangeTargetPositionUseCase changeTargetPositionUseCase = new ChangeTargetPositionUseCaseImpl(lineRepository, domainEventBus);
        ChangeTargetPositionInput input = changeTargetPositionUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setFigureId(lineId);
        input.setTargetPosition(newTargetPosition);

        changeTargetPositionUseCase.execute(input, output);

        assertTrue(lineRepository.findById(lineId).isPresent());
        Line line = lineRepository.findById(lineId).get();
        assertTrue(line.getTargetPosition().equals(newTargetPosition));
        assertEquals(1, eventListener.getEventCount());
    }


}
