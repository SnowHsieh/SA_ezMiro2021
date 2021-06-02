package ntut.csie.team5.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.line.move_endpoint.MoveLineEndpointInput;
import ntut.csie.team5.usecase.figure.line.move_endpoint.MoveLineEndpointUseCase;
import ntut.csie.team5.usecase.figure.line.move_endpoint.MoveLineEndpointUseCaseImpl;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoveLineEndpointUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_move_line_endpoint() {
        MoveLineEndpointUseCase moveLineEndpointUseCase = new MoveLineEndpointUseCaseImpl(lineRepository, domainEventBus);
        MoveLineEndpointInput moveLineEndpointInput = moveLineEndpointUseCase.newInput();
        CqrsCommandPresenter moveLineEndpointOutput = CqrsCommandPresenter.newInstance();

        Endpoint endpointA = new Endpoint(UUID.randomUUID().toString(), 0, 0, "");
        Endpoint endpointB = new Endpoint(UUID.randomUUID().toString(), 10, 10, "");
        String lineId = drawLine(boardId, endpointA, endpointB);
        int newPositionX = endpointA.getPositionX() + 5;
        int newPositionY = endpointA.getPositionY() + 5;

        moveLineEndpointInput.setFigureId(lineId);
        moveLineEndpointInput.setEndpointId(endpointA.getId());
        moveLineEndpointInput.setPositionX(newPositionX);
        moveLineEndpointInput.setPositionY(newPositionY);

        moveLineEndpointUseCase.execute(moveLineEndpointInput, moveLineEndpointOutput);

        assertNotNull(moveLineEndpointOutput.getId());
        assertEquals(ExitCode.SUCCESS, moveLineEndpointOutput.getExitCode());

        Line line = lineRepository.findById(moveLineEndpointOutput.getId()).orElse(null);
        assertNotNull(line);
        assertEquals(moveLineEndpointOutput.getId(), line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals(endpointA.getId(), line.getEndpointA().getId());
        assertEquals(newPositionX, line.getEndpointA().getPositionX());
        assertEquals(newPositionY, line.getEndpointA().getPositionY());
    }
}
