package ntut.csie.team5.usecase.figure.connectable_figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineInput;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineUseCase;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DrawLineUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_draw_line() {
        DrawLineUseCase drawLineUseCase = new DrawLineUseCaseImpl(lineRepository, domainEventBus);
        DrawLineInput drawLineInput = drawLineUseCase.newInput();
        CqrsCommandPresenter drawLineOutput = CqrsCommandPresenter.newInstance();

        drawLineInput.setBoardId(boardId);
        drawLineInput.setEndpointA(new Endpoint(0, 0, ""));
        drawLineInput.setEndpointB(new Endpoint(10, 10, ""));
        drawLineInput.setFigureType(FigureType.LINE);

        drawLineUseCase.execute(drawLineInput, drawLineOutput);

        assertNotNull(drawLineOutput.getId());
        assertEquals(ExitCode.SUCCESS, drawLineOutput.getExitCode());
    }
}
