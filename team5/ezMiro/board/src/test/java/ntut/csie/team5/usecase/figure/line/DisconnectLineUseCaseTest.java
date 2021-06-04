package ntut.csie.team5.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.line.disconnect.DisconnectLineInput;
import ntut.csie.team5.usecase.figure.line.disconnect.DisconnectLineUseCase;
import ntut.csie.team5.usecase.figure.line.disconnect.DisconnectLineUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DisconnectLineUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_disconnect_line() {
        String lineId = connectLineToFigure();
        Line line = lineRepository.findById(lineId).orElse(null);

        DisconnectLineUseCase disconnectLineUseCase = new DisconnectLineUseCaseImpl(lineRepository, domainEventBus);
        DisconnectLineInput disconnectLineInput = disconnectLineUseCase.newInput();
        CqrsCommandPresenter disconnectLineOutput = CqrsCommandPresenter.newInstance();

        disconnectLineInput.setLineId(lineId);
        disconnectLineInput.setEndpointId(line.getEndpointA().getId());

        disconnectLineUseCase.execute(disconnectLineInput, disconnectLineOutput);

        assertNotNull(disconnectLineOutput.getId());
        assertEquals(ExitCode.SUCCESS, disconnectLineOutput.getExitCode());
    }
}
