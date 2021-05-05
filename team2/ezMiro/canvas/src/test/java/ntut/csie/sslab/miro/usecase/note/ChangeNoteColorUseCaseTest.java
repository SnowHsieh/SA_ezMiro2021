package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.DomainEventListener;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorInput;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.color.ChangeNoteColorUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeNoteColorUseCaseTest {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;
    private DomainEventListener eventListener;

    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
        eventListener = new DomainEventListener();

        domainEventBus.register(eventListener);
    }

    @Test
    public void change_note_color(){
        String noteId = create_note();
        ChangeNoteColorUseCase changeNoteColorUseCase = new ChangeNoteColorUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteColorInput input = changeNoteColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setColor("#000000");

        changeNoteColorUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals("#000000", figureRepository.findById(output.getId()).get().getColor());
        assertEquals(2, eventListener.getEventCount());
    }

    private String create_note(){
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId("boardId");
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        return output.getId();
    }
}
