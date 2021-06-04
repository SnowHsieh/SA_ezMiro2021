package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeInput;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeNoteSizeUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void change_note_size() {
        String boardId = create_board();
        String noteId = create_note(boardId);
        eventListener.clear();
        ChangeNoteSizeUseCase changeNoteSizeUseCase = new ChangeNoteSizeUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteSizeInput input = changeNoteSizeUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setHeight(200);
        input.setWidth(200);

        changeNoteSizeUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(200,  figureRepository.findNoteById(output.getId()).get().getHeight());
        assertEquals(200,  figureRepository.findNoteById(output.getId()).get().getWidth());
        assertEquals(1, eventListener.getEventCount());
    }
}