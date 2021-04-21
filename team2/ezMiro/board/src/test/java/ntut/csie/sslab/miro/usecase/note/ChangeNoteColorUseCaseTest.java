package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.NoteRepositoryImpl;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.ChangeNoteColorInput;
import ntut.csie.sslab.miro.usecase.note.edit.ChangeNoteColorUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.ChangeNoteColorUseCaseImpl;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeNoteColorUseCaseTest {
    public NoteRepository noteRepository;
    public DomainEventBus domainEventBus;

    @Test
    public void change_note_color(){
        String noteId = create_note();

        ChangeNoteColorUseCase changeNoteColorUseCase = new ChangeNoteColorUseCaseImpl(noteRepository, domainEventBus);
        ChangeNoteColorInput input = changeNoteColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setNoteId(noteId);
        input.setNewColor("#000000");

        changeNoteColorUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals("#000000", noteRepository.findById(output.getId()).get().getColor());
    }

    public String create_note(){
        noteRepository = new NoteRepositoryImpl();
        domainEventBus = new DomainEventBus();
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(noteRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId("boardId");
        createNoteUseCase.execute(input, output);

        return output.getId();
    }
}
