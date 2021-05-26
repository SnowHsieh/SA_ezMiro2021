package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.connectable_figure.note.change_color.ChangeNoteColorInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.change_color.ChangeNoteColorUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.change_color.ChangeNoteColorUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChangeNoteColorUseCaseTest extends AbstractTest {

    @Test
    public void should_success_when_note_color_changed() {
        ChangeNoteColorUseCase changeNoteColorUseCase = new ChangeNoteColorUseCaseImpl(noteRepository, domainEventBus);
        ChangeNoteColorInput changeNoteColorInput = changeNoteColorUseCase.newInput();
        CqrsCommandPresenter changeNoteColorOutput = CqrsCommandPresenter.newInstance();

        String noteId = postNote(boardId, defaultLeftTopPositionX, defaultLeftTopPositionY, defaultHeight, defaultWidth, defaultColor);
        String newColor = "#000000";

        changeNoteColorInput.setFigureId(noteId);
        changeNoteColorInput.setColor(newColor);

        changeNoteColorUseCase.execute(changeNoteColorInput, changeNoteColorOutput);
        assertNotNull(changeNoteColorOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeNoteColorOutput.getExitCode());

        Note note = noteRepository.findById(changeNoteColorOutput.getId()).orElse(null);
        assertNotNull(note);
        assertEquals(changeNoteColorOutput.getId(), note.getId());
        assertEquals(boardId, note.getBoardId());
        assertEquals(newColor, note.getColor());
    }
}
