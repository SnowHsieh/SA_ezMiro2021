package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCaseImpl;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class EditNoteTextUseCaseTest extends AbstractTest {
    @Test
    public void should_success_when_note_text_edited() {
        EditNoteTextUseCase editNoteTextUseCase = new EditNoteTextUseCaseImpl(figureRepository, domainEventBus);
        EditNoteTextInput editNoteTextInput = editNoteTextUseCase.newInput();
        CqrsCommandPresenter editNoteTextOutput = CqrsCommandPresenter.newInstance();

        String noteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String newText = "hi";

        editNoteTextInput.setFigureId(noteId);
        editNoteTextInput.setText(newText);

        editNoteTextUseCase.execute(editNoteTextInput, editNoteTextOutput);
        assertNotNull(editNoteTextOutput.getId());
        assertEquals(ExitCode.SUCCESS, editNoteTextOutput.getExitCode());

        Figure figure = figureRepository.findById(editNoteTextOutput.getId()).get();
        assertTrue(figure instanceof Note);
        Note note = (Note) figure;
        assertEquals(editNoteTextOutput.getId(), note.getId());
        assertEquals(boardId, note.getBoardId());
        assertEquals(newText, note.getText());
    }
}
