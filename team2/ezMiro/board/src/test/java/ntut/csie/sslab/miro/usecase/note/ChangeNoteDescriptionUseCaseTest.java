package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.NoteRepositoryImpl;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.ChangeNoteDescriptionInput;
import ntut.csie.sslab.miro.usecase.note.edit.ChangeNoteDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.ChangeNoteDescriptionUseCaseImpl;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeNoteDescriptionUseCaseTest {
    public NoteRepository noteRepository;
    public DomainEventBus domainEventBus;

    @Test
    public void change_note_description() {

        String noteId = create_note();

        ChangeNoteDescriptionUseCase changeNoteDescriptionUseCase = new ChangeNoteDescriptionUseCaseImpl(noteRepository, domainEventBus);
        ChangeNoteDescriptionInput input = changeNoteDescriptionUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setNoteId(noteId);
        input.setNewDescription("tttest");

        changeNoteDescriptionUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals("tttest", noteRepository.findById(output.getId()).get().getDescription());
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
