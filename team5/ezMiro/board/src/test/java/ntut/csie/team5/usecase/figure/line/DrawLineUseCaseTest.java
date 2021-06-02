package ntut.csie.team5.usecase.figure.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.line.Endpoint;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineInput;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineUseCase;
import ntut.csie.team5.usecase.figure.line.draw.DrawLineUseCaseImpl;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DrawLineUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_draw_line() {
        DrawLineUseCase drawLineUseCase = new DrawLineUseCaseImpl(lineRepository, domainEventBus);
        DrawLineInput drawLineInput = drawLineUseCase.newInput();
        CqrsCommandPresenter drawLineOutput = CqrsCommandPresenter.newInstance();

        Endpoint endpointA = new Endpoint(UUID.randomUUID().toString(), 0, 0, "");
        Endpoint endpointB = new Endpoint(UUID.randomUUID().toString(), 10, 10, "");

        drawLineInput.setBoardId(boardId);
        drawLineInput.setEndpointA(endpointA);
        drawLineInput.setEndpointB(endpointB);
        drawLineInput.setFigureType(FigureType.LINE);

        drawLineUseCase.execute(drawLineInput, drawLineOutput);

        assertNotNull(drawLineOutput.getId());
        assertEquals(ExitCode.SUCCESS, drawLineOutput.getExitCode());

        Line line = lineRepository.findById(drawLineOutput.getId()).orElse(null);
        assertNotNull(line);
        assertEquals(drawLineOutput.getId(), line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals(endpointA, line.getEndpointA());
        assertEquals(endpointB, line.getEndpointB());
        assertEquals(FigureType.LINE, line.getFigureType());
    }

    @Test
    public void should_succeed_when_draw_line_in_board() {
        String boardId = createBoard(projectId, boardName);
        Board board = boardRepository.findById(boardId).orElse(null);
        assertNotNull(board);

        Endpoint endpointA = new Endpoint(UUID.randomUUID().toString(), 0, 0, "");
        Endpoint endpointB = new Endpoint(UUID.randomUUID().toString(), 10, 10, "");

        String firstLineId = drawLine(boardId, endpointA, endpointB);

        board.commitFigure(firstLineId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).orElse(null);
        assertNotNull(board);
        assertEquals(1, board.getCommittedFigures().size());
    }

//    @Test
//    public void should_succeed_when_draw_line_with_a_figure_connected() {
//        String noteId = postNote(boardId, )
//    }
//
//    @Test
//    public void should_succeed_when_draw_line_with_two_figures_connected() {
//
//    }
}
