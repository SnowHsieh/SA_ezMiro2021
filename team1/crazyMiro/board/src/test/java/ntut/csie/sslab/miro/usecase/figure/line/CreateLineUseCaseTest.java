package ntut.csie.sslab.miro.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.figure.line.create.CreateLineInput;
import ntut.csie.sslab.miro.usecase.figure.line.create.CreateLineUseCase;
import ntut.csie.sslab.miro.usecase.figure.line.create.CreateLineUseCaseImpl;
import org.junit.jupiter.api.Test;

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
        assertEquals(-1, line.getSourcePosition().getX());
        assertEquals(-1, line.getSourcePosition().getY());
        assertEquals(-1, line.getTargetPosition().getX());
        assertEquals(-1, line.getTargetPosition().getY());
        assertEquals(1, eventListener.getEventCount());



    }
}
