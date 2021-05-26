package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.connectable_figure.note.resize.ResizeNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.resize.ResizeNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.resize.ResizeNoteUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResizeNoteUseCaseTest extends AbstractTest {

    @Test
    public void should_success_when_note_resized() {
        ResizeNoteUseCase resizeNoteUseCase = new ResizeNoteUseCaseImpl(noteRepository, domainEventBus);
        ResizeNoteInput resizeNoteInput = resizeNoteUseCase.newInput();
        CqrsCommandPresenter resizeNoteOutput = CqrsCommandPresenter.newInstance();

        String noteId = postNote(boardId, defaultLeftTopPositionX, defaultLeftTopPositionY, defaultHeight, defaultWidth, defaultColor);
        int newHeight = defaultHeight + 10;
        int newWidth = defaultWidth + 10;

        resizeNoteInput.setFigureId(noteId);
        resizeNoteInput.setHeight(newHeight);
        resizeNoteInput.setWidth(newWidth);

        resizeNoteUseCase.execute(resizeNoteInput, resizeNoteOutput);
        assertNotNull(resizeNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, resizeNoteOutput.getExitCode());

        Note note = noteRepository.findById(resizeNoteOutput.getId()).orElse(null);
        assertNotNull(note);
        assertEquals(resizeNoteOutput.getId(), note.getId());
        assertEquals(boardId, note.getBoardId());
        assertEquals(newHeight, note.getHeight());
        assertEquals(newWidth, note.getWidth());
    }
}
