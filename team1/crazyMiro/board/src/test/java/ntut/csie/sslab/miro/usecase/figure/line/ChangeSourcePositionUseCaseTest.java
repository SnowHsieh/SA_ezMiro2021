package ntut.csie.sslab.miro.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionInput;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeSourcePositionUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void change_a_line_source_position() {
        String boardId = UUID.randomUUID().toString();
        Coordinate newSourcePosition = new Coordinate(new Random().nextLong(), new Random().nextLong());
        String lineId = createLine(boardId,
                "-1",
                "-1",
                new Coordinate(new Random().nextLong(), new Random().nextLong()),
                new Coordinate(new Random().nextLong(), new Random().nextLong()));
        eventListener.clearEventCount();
        ChangeSourcePositionUseCase changeSourcePositionUseCase = new ChangeSourcePositionUseCaseImpl(lineRepository, domainEventBus);
        ChangeSourcePositionInput input = changeSourcePositionUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setFigureId(lineId);
        input.setSourcePosition(newSourcePosition);

        changeSourcePositionUseCase.execute(input, output);

        assertTrue(lineRepository.findById(lineId).isPresent());
        Line line = lineRepository.findById(lineId).get();
        assertTrue(line.getSourcePosition().equals(newSourcePosition));
        assertEquals(1, eventListener.getEventCount());
    }


}
