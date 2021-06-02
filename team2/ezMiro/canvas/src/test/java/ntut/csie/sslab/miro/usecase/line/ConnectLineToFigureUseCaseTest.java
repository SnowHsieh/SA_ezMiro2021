package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.ArrowStyle;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.line.connecttofigure.ConnectLineToFigureInput;
import ntut.csie.sslab.miro.usecase.line.connecttofigure.ConnectLineToFigureUseCase;
import ntut.csie.sslab.miro.usecase.line.connecttofigure.ConnectLineToFigureUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectLineToFigureUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void connect_line_to_figure() {
        String boardId = create_board();
        String noteId = create_note(boardId);
        String lineId = create_line_without_connected_figures(boardId);
        eventListener.clear();
        ConnectLineToFigureUseCase connectLineToFigureUseCase = new ConnectLineToFigureUseCaseImpl(figureRepository, domainEventBus);
        ConnectLineToFigureInput input = connectLineToFigureUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);
        input.setLinePoint(LinePoint.START);
        input.setFigureId(noteId);
        input.setOffset(new Coordinate(0, 10));

        connectLineToFigureUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals(noteId, line.getStartConnectableFigureId());
        assertEquals("", line.getEndConnectableFigureId());
        assertEquals(0, line.getStartOffset().getX(), 0.001);
        assertEquals(10, line.getStartOffset().getY(), 0.001);
        assertEquals(40, line.getEndOffset().getX(), 0.001);
        assertEquals(50, line.getEndOffset().getY(), 0.001);
        assertEquals(ArrowStyle.NONE, line.getStartArrowStyle());
        assertEquals(ArrowStyle.STANDARD, line.getEndArrowStyle());
        assertEquals(1, eventListener.getEventCount());
    }
}