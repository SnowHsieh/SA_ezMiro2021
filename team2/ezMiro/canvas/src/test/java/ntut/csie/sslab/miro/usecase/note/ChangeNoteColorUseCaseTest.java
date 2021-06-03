package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorInput;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeNoteColorUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void change_note_color(){
        String boardId = create_board();
        String noteId = create_note(boardId);
        eventListener.clear();
        ChangeNoteColorUseCase changeNoteColorUseCase = new ChangeNoteColorUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteColorInput input = changeNoteColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setColor("#000000");

        changeNoteColorUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals("#000000", figureRepository.findNoteById(output.getId()).get().getColor());
        assertEquals(1, eventListener.getEventCount());
    }
}