package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.DomainEventListener;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionInput;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.description.ChangeNoteDescriptionUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeNoteDescriptionUseCaseTest {
    public FigureRepository figureRepository;
    public DomainEventBus domainEventBus;
    public DomainEventListener eventListener;

    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
        eventListener = new DomainEventListener();

        domainEventBus.register(eventListener);
    }

    @Test
    public void change_note_description() {
        String noteId = create_note();
        ChangeNoteDescriptionUseCase changeNoteDescriptionUseCase = new ChangeNoteDescriptionUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteDescriptionInput input = changeNoteDescriptionUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setNewDescription("test");

        changeNoteDescriptionUseCase.execute(input, output);

        Note note = (Note)figureRepository.findById(output.getId()).get();
        // TODO: Type cast need to fix.
        assertNotNull(output.getId());
        assertEquals("test", note.getDescription());
        assertEquals(2, eventListener.getEventCount());
    }

    private String create_note(){
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId("boardId");
        input.setCoordinate(new Coordinate(new Point(9,26)));

        createNoteUseCase.execute(input, output);

        return output.getId();
    }

}
