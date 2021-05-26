package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class EditNoteTextUseCaseTest extends AbstractTest {

    @Test
    public void should_success_when_note_text_edited() {
        EditNoteTextUseCase editNoteTextUseCase = new EditNoteTextUseCaseImpl(noteRepository, domainEventBus);
        EditNoteTextInput editNoteTextInput = editNoteTextUseCase.newInput();
        CqrsCommandPresenter editNoteTextOutput = CqrsCommandPresenter.newInstance();

        String noteId = postNote(boardId, defaultLeftTopPositionX, defaultLeftTopPositionY, defaultHeight, defaultWidth, defaultColor);
        String newText = "hi";

        editNoteTextInput.setFigureId(noteId);
        editNoteTextInput.setText(newText);

        editNoteTextUseCase.execute(editNoteTextInput, editNoteTextOutput);
        assertNotNull(editNoteTextOutput.getId());
        assertEquals(ExitCode.SUCCESS, editNoteTextOutput.getExitCode());

        Note note = noteRepository.findById(editNoteTextOutput.getId()).orElse(null);
        assertNotNull(note);
        assertEquals(editNoteTextOutput.getId(), note.getId());
        assertEquals(boardId, note.getBoardId());
        assertEquals(newText, note.getText());
    }
}
