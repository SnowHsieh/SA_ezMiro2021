package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.ArrowStyle;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineInput;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCase;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateLineUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void create_line_without_connected_figures() {
        String boardId = create_board();
        eventListener.clear();
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(figureRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId("");
        input.setEndConnectableFigureId("");
        input.setStartOffset(new Coordinate(20, 30));
        input.setEndOffset(new Coordinate(40, 50));

        createLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals("", line.getStartConnectableFigureId());
        assertEquals("", line.getEndConnectableFigureId());
        assertEquals(20, line.getStartOffset().getX(), 0.001);
        assertEquals(30, line.getStartOffset().getY(), 0.001);
        assertEquals(40, line.getEndOffset().getX(), 0.001);
        assertEquals(50, line.getEndOffset().getY(), 0.001);
        assertEquals(ArrowStyle.NONE, line.getStartArrowStyle());
        assertEquals(ArrowStyle.STANDARD, line.getEndArrowStyle());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void create_line_with_one_connected_figure_at_start_point() {
        String boardId = create_board();
        String noteId = create_note(boardId);
        eventListener.clear();
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(figureRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId(noteId);
        input.setEndConnectableFigureId("");
        input.setStartOffset(new Coordinate(20, 30));
        input.setEndOffset(new Coordinate(40, 50));

        createLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals(noteId, line.getStartConnectableFigureId());
        assertEquals("", line.getEndConnectableFigureId());
        assertEquals(20, line.getStartOffset().getX(), 0.001);
        assertEquals(30, line.getStartOffset().getY(), 0.001);
        assertEquals(40, line.getEndOffset().getX(), 0.001);
        assertEquals(50, line.getEndOffset().getY(), 0.001);
        assertEquals(ArrowStyle.NONE, line.getStartArrowStyle());
        assertEquals(ArrowStyle.STANDARD, line.getEndArrowStyle());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void create_line_with_one_connected_figure_at_end_point() {
        String boardId = create_board();
        String noteId = create_note(boardId);
        eventListener.clear();
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(figureRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId("");
        input.setEndConnectableFigureId(noteId);
        input.setStartOffset(new Coordinate(20, 30));
        input.setEndOffset(new Coordinate(40, 50));

        createLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals("", line.getStartConnectableFigureId());
        assertEquals(noteId, line.getEndConnectableFigureId());
        assertEquals(20, line.getStartOffset().getX(), 0.001);
        assertEquals(30, line.getStartOffset().getY(), 0.001);
        assertEquals(40, line.getEndOffset().getX(), 0.001);
        assertEquals(50, line.getEndOffset().getY(), 0.001);
        assertEquals(ArrowStyle.NONE, line.getStartArrowStyle());
        assertEquals(ArrowStyle.STANDARD, line.getEndArrowStyle());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void create_line_with_two_connected_figures() {
        String boardId = create_board();
        String startNoteId = create_note(boardId);
        String endNoteId = create_note(boardId);
        eventListener.clear();
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(figureRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId(startNoteId);
        input.setEndConnectableFigureId(endNoteId);
        input.setStartOffset(new Coordinate(20, 30));
        input.setEndOffset(new Coordinate(40, 50));

        createLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        Line line = figureRepository.findLineById(output.getId()).get();
        assertNotNull(line.getId());
        assertEquals(boardId, line.getBoardId());
        assertEquals(startNoteId, line.getStartConnectableFigureId());
        assertEquals(endNoteId, line.getEndConnectableFigureId());
        assertEquals(20, line.getStartOffset().getX(), 0.001);
        assertEquals(30, line.getStartOffset().getY(), 0.001);
        assertEquals(40, line.getEndOffset().getX(), 0.001);
        assertEquals(50, line.getEndOffset().getY(), 0.001);
        assertEquals(ArrowStyle.NONE, line.getStartArrowStyle());
        assertEquals(ArrowStyle.STANDARD, line.getEndArrowStyle());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void should_commit_figure_to_board_when_line_created(){
        String boardId = create_board();
        eventListener.clear();
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(figureRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId("");
        input.setEndConnectableFigureId("");
        input.setStartOffset(new Coordinate(20, 30));
        input.setEndOffset(new Coordinate(40, 50));

        createLineUseCase.execute(input, output);

        assertEquals(2, eventListener.getEventCount());
        Board board = boardRepository.findById(boardId).get();
        assertEquals(1, board.getCommittedFigures().size());
        assertEquals(output.getId(), board.getCommittedFigures().get(output.getId()).getFigureId());
        assertEquals(0, board.getCommittedFigures().get(output.getId()).getZOrder());
    }
}