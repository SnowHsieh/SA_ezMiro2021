package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionInput;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeNoteDescriptionUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void change_note_description() {
        String boardId = create_board();
        String noteId = create_note(boardId);
        eventListener.clear();
        ChangeNoteDescriptionUseCase changeNoteDescriptionUseCase = new ChangeNoteDescriptionUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteDescriptionInput input = changeNoteDescriptionUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setDescription("test");

        changeNoteDescriptionUseCase.execute(input, output);

        Note note = figureRepository.findNoteById(output.getId()).get();
        assertNotNull(output.getId());
        assertEquals("test", note.getDescription());
        assertEquals(1, eventListener.getEventCount());
    }
}