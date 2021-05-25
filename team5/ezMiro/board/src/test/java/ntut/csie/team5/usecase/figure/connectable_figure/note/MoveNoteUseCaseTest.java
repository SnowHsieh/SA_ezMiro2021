package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCaseImpl;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class MoveNoteUseCaseTest extends AbstractTest {

    @Test
    public void should_success_when_note_moved() {
        MoveNoteUseCase moveNoteUseCase = new MoveNoteUseCaseImpl(figureRepository, domainEventBus);
        MoveNoteInput moveNoteInput = moveNoteUseCase.newInput();
        CqrsCommandPresenter moveNoteOutput = CqrsCommandPresenter.newInstance();

        String noteId = postNote(boardId, defaultLeftTopPositionX, defaultLeftTopPositionY, defaultHeight, defaultWidth, defaultColor);
        int nextLeftTopPositionX = defaultLeftTopPositionX + 100;
        int nextLeftTopPositionY = defaultLeftTopPositionY + 100;

        moveNoteInput.setFigureId(noteId);
        moveNoteInput.setLeftTopPositionX(nextLeftTopPositionX);
        moveNoteInput.setLeftTopPositionY(nextLeftTopPositionY);

        moveNoteUseCase.execute(moveNoteInput, moveNoteOutput);
        assertNotNull(moveNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, moveNoteOutput.getExitCode());

        Figure figure = figureRepository.findById(moveNoteOutput.getId()).get();
        assertTrue(figure instanceof Note);
        Note note = (Note) figure;
        assertEquals(moveNoteOutput.getId(), note.getId());
        assertEquals(boardId, note.getBoardId());
        assertEquals(nextLeftTopPositionX, note.getLeftTopPositionX());
        assertEquals(nextLeftTopPositionY, note.getLeftTopPositionY());
    }
}
