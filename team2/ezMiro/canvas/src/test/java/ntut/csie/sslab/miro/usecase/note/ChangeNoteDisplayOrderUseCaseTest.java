package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.edit.displayorder.ChangeNoteDisplayOrderInput;
import ntut.csie.sslab.miro.usecase.note.edit.displayorder.ChangeNoteDisplayOrderUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.displayorder.ChangeNoteDisplayOrderUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeNoteDisplayOrderUseCaseTest {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void change_note_display_order() {
        String noteId = create_note();
        ChangeNoteDisplayOrderUseCase changeNoteDisplayOrderUseCase = new ChangeNoteDisplayOrderUseCaseImpl(figureRepository, domainEventBus);
        ChangeNoteDisplayOrderInput input = changeNoteDisplayOrderUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setDisplayOrder(2);

        changeNoteDisplayOrderUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(2,  figureRepository.findById(output.getId()).get().getDisplayOrder());
    }

    private String create_note() {
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId("boardId");
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        return output.getId();
    }
}