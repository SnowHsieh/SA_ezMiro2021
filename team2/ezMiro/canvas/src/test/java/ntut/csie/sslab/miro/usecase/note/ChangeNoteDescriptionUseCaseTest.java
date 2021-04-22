package ntut.csie.sslab.miro.usecase.note;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.NoteRepositoryImpl;
import ntut.csie.sslab.miro.usecase.DomainEventListener;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionInput;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.EventListener;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeNoteDescriptionUseCaseTest {
    public NoteRepository noteRepository;
    public DomainEventBus domainEventBus;
    public DomainEventListener eventListener;

    @Before
    public void setUp() {
        noteRepository = new NoteRepositoryImpl();
        domainEventBus = new DomainEventBus();
        eventListener = new DomainEventListener();

        domainEventBus.register(eventListener);
    }

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
        assertEquals(2, eventListener.getEventCount());
    }

    private String create_note(){
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(noteRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId("boardId");
        createNoteUseCase.execute(input, output);

        return output.getId();
    }

}
