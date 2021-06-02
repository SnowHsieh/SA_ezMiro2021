package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineInput;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteLineUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void delete_a_line_connect_two_figure() {
        String boardId = UUID.randomUUID().toString();
        String sourceFigureId = UUID.randomUUID().toString();
        String targetFigureId = UUID.randomUUID().toString();
        String lineId = createLine(boardId, sourceFigureId, targetFigureId, null, null);
        eventListener.clearEventCount();
        DeleteLineUseCase deleteLineUseCase = new DeleteLineUseCaseImpl(lineRepository, domainEventBus);
        DeleteLineInput input = deleteLineUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);

        deleteLineUseCase.execute(input, output);

        assertFalse(lineRepository.findById(output.getId()).isPresent());
        assertEquals(1, eventListener.getEventCount());
    }
}
