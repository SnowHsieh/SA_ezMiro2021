package ntut.csie.team5.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.line.move.MoveLineInput;
import ntut.csie.team5.usecase.figure.line.move.MoveLineUseCase;
import ntut.csie.team5.usecase.figure.line.move.MoveLineUseCaseImpl;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoveLineUseCaseTest  extends AbstractTest {

    @Test
    public void should_succeed_when_move_line() {
        Endpoint endpointA = new Endpoint(UUID.randomUUID().toString(), 0, 0, "");
        Endpoint endpointB = new Endpoint(UUID.randomUUID().toString(), 10, 10, "");
        String lineId = drawLine(boardId, endpointA, endpointB);
        int offsetX = 10;
        int offsetY = 10;

        MoveLineUseCase moveLineUseCase = new MoveLineUseCaseImpl(lineRepository, domainEventBus);
        MoveLineInput moveLineInput = moveLineUseCase.newInput();
        CqrsCommandPresenter moveLineOutput = CqrsCommandPresenter.newInstance();

        moveLineInput.setFigureId(lineId);
        moveLineInput.setOffsetX(offsetX);
        moveLineInput.setOffsetY(offsetY);

        moveLineUseCase.execute(moveLineInput, moveLineOutput);

        assertNotNull(moveLineOutput.getId());
        assertEquals(ExitCode.SUCCESS, moveLineOutput.getExitCode());

        Line line = lineRepository.findById(moveLineOutput.getId()).orElse(null);
        assertNotNull(line);
        assertEquals(moveLineOutput.getId(), line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals(endpointA.getPositionX() + offsetX, line.getEndpointA().getPositionX());
        assertEquals(endpointA.getPositionY() + offsetY, line.getEndpointA().getPositionY());
        assertEquals(endpointB.getPositionX() + offsetX, line.getEndpointB().getPositionX());
        assertEquals(endpointB.getPositionY() + offsetY, line.getEndpointB().getPositionY());
    }
}