package ntut.csie.team5.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.line.delete.DeleteLineInput;
import ntut.csie.team5.usecase.figure.line.delete.DeleteLineUseCase;
import ntut.csie.team5.usecase.figure.line.delete.DeleteLineUseCaseImpl;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class DeleteLineUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_delete_line() {
        Endpoint endpointA = new Endpoint(UUID.randomUUID().toString(), 0, 0, "");
        Endpoint endpointB = new Endpoint(UUID.randomUUID().toString(), 10, 10, "");
        String lineId = drawLine(boardId, endpointA, endpointB);

        DeleteLineUseCase deleteLineUseCase = new DeleteLineUseCaseImpl(lineRepository, domainEventBus);
        DeleteLineInput deleteLineInput = deleteLineUseCase.newInput();
        CqrsCommandPresenter deleteLineOutput = CqrsCommandPresenter.newInstance();

        deleteLineInput.setFigureId(lineId);

        deleteLineUseCase.execute(deleteLineInput, deleteLineOutput);

        assertNotNull(deleteLineOutput.getId());
        assertEquals(ExitCode.SUCCESS, deleteLineOutput.getExitCode());
        assertFalse(lineRepository.findById(deleteLineOutput.getId()).isPresent());
    }
}
