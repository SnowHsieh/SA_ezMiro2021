package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.Note;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateNoteUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void create_note() {
        String boardId = create_board();
        eventListener.clear();
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        Note note = figureRepository.findNoteById(output.getId()).get();
        assertNotNull(note);
        assertEquals(9, note.getCoordinate().getX());
        assertEquals(26, note.getCoordinate().getY());
        assertEquals(100, note.getWidth());
        assertEquals(100, note.getHeight());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void should_commit_figure_to_board_when_note_created(){
        String boardId = create_board();
        eventListener.clear();
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        assertEquals(2, eventListener.getEventCount());
        Board board = boardRepository.findById(boardId).get();
        assertEquals(1, board.getCommittedFigures().size());
        assertEquals(output.getId(), board.getCommittedFigures().get(output.getId()).getFigureId());
        assertEquals(0, board.getCommittedFigures().get(output.getId()).getZOrder());
    }
}