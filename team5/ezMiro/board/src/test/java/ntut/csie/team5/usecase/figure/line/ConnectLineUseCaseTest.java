package ntut.csie.team5.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.line.connect.ConnectLineInput;
import ntut.csie.team5.usecase.figure.line.connect.ConnectLineUseCase;
import ntut.csie.team5.usecase.figure.line.connect.ConnectLineUseCaseImpl;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConnectLineUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_connect_line() {
        ConnectLineUseCase connectLineUseCase = new ConnectLineUseCaseImpl(lineRepository, domainEventBus);
        ConnectLineInput connectLineInput = connectLineUseCase.newInput();
        CqrsCommandPresenter connectLineOutput = CqrsCommandPresenter.newInstance();

        String noteId = postNote(boardId, defaultLeftTopPositionX, defaultLeftTopPositionY, defaultHeight, defaultWidth, defaultColor);

        Endpoint endpointA = new Endpoint(UUID.randomUUID().toString(), 0, 0, "");
        Endpoint endpointB = new Endpoint(UUID.randomUUID().toString(), 10, 10, "");
        String lineId = drawLine(boardId, endpointA, endpointB);
        Line line = lineRepository.findById(lineId).orElse(null);

        connectLineInput.setFigureId(lineId);
        connectLineInput.setEndpointId(line.getEndpointA().getId());
        connectLineInput.setConnectedFigureId(noteId);

        connectLineUseCase.execute(connectLineInput, connectLineOutput);

        assertNotNull(connectLineOutput.getId());
        assertEquals(ExitCode.SUCCESS, connectLineOutput.getExitCode());
    }
}
