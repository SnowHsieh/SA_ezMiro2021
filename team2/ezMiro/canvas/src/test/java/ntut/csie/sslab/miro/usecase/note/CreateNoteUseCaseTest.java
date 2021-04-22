package ntut.csie.sslab.miro.usecase.note;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.NoteRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.entity.model.note.event.NoteCreated;
import ntut.csie.sslab.miro.usecase.DomainEventListener;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateNoteUseCaseTest {
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
    public void create_note() {
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(noteRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId("boardId");
        createNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertNotNull(noteRepository.findById(output.getId()).get());
        assertEquals(1, eventListener.getEventCount());
    }

}
