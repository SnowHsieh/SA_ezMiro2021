package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.line.Line;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineInput;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCase;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateLineUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void create_a_line_which_connect_two_figure() {
        String boardId = UUID.randomUUID().toString();
        String sourceFigureId = UUID.randomUUID().toString();
        String targetFigureId = UUID.randomUUID().toString();
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(lineRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setSourceId(sourceFigureId);
        input.setTargetId(targetFigureId);
        input.setSourcePosition(null);
        input.setTargetPosition(null);

        createLineUseCase.execute(input, output);

        assertTrue(lineRepository.findById(output.getId()).isPresent());
        assertNotNull(output.getId());
        Line line = lineRepository.findById(output.getId()).get();
        assertEquals(sourceFigureId, line.getSourceId());
        assertEquals(targetFigureId, line.getTargetId());
        assertNull(line.getSourcePosition());
        assertNull(line.getTargetPosition());
        assertEquals(1, eventListener.getEventCount());



    }
}
