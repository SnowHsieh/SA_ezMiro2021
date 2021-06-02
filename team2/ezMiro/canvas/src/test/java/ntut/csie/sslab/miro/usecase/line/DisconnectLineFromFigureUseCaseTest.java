package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.line.disconnectfromfigure.DisconnectLineFromFigureInput;
import ntut.csie.sslab.miro.usecase.line.disconnectfromfigure.DisconnectLineFromFigureUseCase;
import ntut.csie.sslab.miro.usecase.line.disconnectfromfigure.DisconnectLineFromFigureUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DisconnectLineFromFigureUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void disconnect_line_from_figure(){
        String boardId = create_board();
        String noteId = create_note(boardId);
        String lineId = create_line_connected_to_figure_at_start_point(boardId, noteId);
        eventListener.clear();
        DisconnectLineFromFigureUseCase disconnectLineFromFigureUseCase = new DisconnectLineFromFigureUseCaseImpl(figureRepository, domainEventBus);
        DisconnectLineFromFigureInput input = disconnectLineFromFigureUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);
        input.setFigureId(noteId);
        input.setLinePoint(LinePoint.START);
        input.setPointOffset(new Coordinate(50, 50));

        disconnectLineFromFigureUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals("", line.getStartConnectableFigureId());
        assertEquals(50, line.getStartOffset().getX(), 0.001);
        assertEquals(50, line.getStartOffset().getY(), 0.001);
        assertEquals(1, eventListener.getEventCount());
    }
}